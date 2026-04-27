package com.student.orders.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.orders.components.OrderKafka;
import com.student.orders.dto.BookTableList;
import com.student.orders.dto.Checkout;
import com.student.orders.errors.IllegalResponseException;
import com.student.orders.events.OrderCheck;
import com.student.orders.events.OrderCreated;
import com.student.orders.events.OrderFeedback;
import com.student.orders.global.CreateOrder;
import com.student.orders.global.interfaces.CheckOutQuery;
import com.student.orders.mappers.CheckoutMapper;
import com.student.orders.model.BooksModels;
import com.student.orders.model.CheckOutModel;
import com.student.orders.model.OrdersModel;
import com.student.orders.repository.BookRepositery;
import com.student.orders.repository.CheckRepository;
import com.student.orders.repository.OrderRepository;

@Service
public class OrdersServices {

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepositery BookRepository;

    @Autowired
    private CheckoutMapper MapperCheck;

    private final OrderKafka kafkaOrders;

    // Utilizzo del Costruttore con Kafka
    public OrdersServices(OrderKafka orderKafka){
        this.kafkaOrders = orderKafka;
    }

    public List<Checkout> getAllOrders(int ordineId) {
        List<Object[]> checkList = checkRepository.findAllConTitolo(ordineId);
         if (checkList == null || checkList.isEmpty()) {
            return Collections.emptyList();
        }

        List<CheckOutQuery> queries = checkList.stream()
            .map(arr -> new CheckOutQuery(
                (Integer) arr[0],  
                (String) arr[1], 
                (Integer) arr[2],
                (BigDecimal) arr[3], 
                (Integer) arr[4],  
                (BigDecimal) arr[5],
                (String) arr[6],
                (String) arr[7],
                (String) arr[8]
            ))
            .toList();

        kafkaOrders.sendFeedBack(
            new OrderFeedback(
                1,
                "DB-FeedBack",
                true
            )
        );

        return MapperCheck.map(queries);
    }

    public boolean deleteProd(int id) {
        if (checkRepository.existsById(id)) {
            checkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteOrder(int id) {
        List<CheckOutModel> checkList = checkRepository.findByOrdineId(id);
        System.out.println(checkList);
        if (checkList.size() != 0) {
            checkRepository.deleteByOrdineId(id);
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public HashMap<String, Object> createOrder(CreateOrder orders) {
        OrdersModel order = new OrdersModel();
        List<CheckOutModel> check = new ArrayList<>();
        HashMap<String, Object> results = new HashMap<>();
        int id = 0;

        if (orders.getTotal() == 0) {
            throw new IllegalResponseException("Il totale dell'ordine deve essere maggiore di zero");
        }

        order.setPrezzoTotale(BigDecimal.valueOf(orders.getTotal()));

        if (orders.getShop() == null || orders.getShop().isEmpty()) {
            throw new IllegalResponseException("La lista dei prodotti non può essere vuota");
        }

        for (BookTableList prod : orders.getShop()) {
            CheckOutModel checkOut = new CheckOutModel();
            BooksModels bookFind = BookRepository.findAllById(prod.libro_id()).stream().findFirst().orElse(null);
            checkOut.setLibro(bookFind);
            checkOut.setOrdine(order);
            checkOut.setLibroPrezzo(prod.libro_prezzo());
            checkOut.setQuantita(prod.quantita());
            checkOut.setPrezzoSubtotale(prod.prezzo_subtotale());
            check.add(checkOut);
            kafkaOrders.sendOrderCreated(
                new OrderCreated(
                    bookFind.getId(),
                    bookFind.getTitolo(),
                    order.getId(),
                    bookFind.getPrezzo(),
                    checkOut.getQuantita(),
                    checkOut.getPrezzoSubtotale()
                )
            );
        }

        if (check != null && !check.isEmpty()) {
            order.setRigheCheckout(check);
            OrdersModel saves = orderRepository.save(order);
            id = saves.getId();
            results.put("status", false);
            results.put("id", 0);
        }
        
        results.put("status", true);
        results.put("id", id);
        return results;
    }

    public boolean checkOrderExists(List<BookTableList> prod) {
        if (prod == null || prod.isEmpty()) return false;

        // Ricerca dei libri basate sulla lista di id effettiva
        List<Integer> requestedId = prod.stream()
            .map(item -> item.libro_id().intValue())
            .toList();

        List<Integer> foundIds = BookRepository.findExistingIds(requestedId);

        return foundIds.containsAll(requestedId);
    }

    public Map<String, Boolean> checkOrderAfterBuy(int id){
        Optional<OrdersModel> FindOrder = orderRepository.findById(id);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("orderFound", false);
        response.put("orderCompleted", false);

        if(FindOrder.isPresent()) {
            response.put("orderFound", true);
            if (FindOrder.get().getOrdinato() == false) {
                response.put("orderCompleted", true);
            }
        } 

        if (FindOrder.isPresent() && FindOrder.get().getOrdinato() == false) {
            OrdersModel orderToUpdate = FindOrder.get();
            orderToUpdate.setOrdinato(true);
            orderRepository.save(orderToUpdate);

            // Invio del Evento al Consumer del Broker Kafka
            kafkaOrders.sendOrderCompleted(
                new OrderCheck(FindOrder.get().getId(), FindOrder.get().getPrezzoTotale(), FindOrder.get().getOrdinato())
            );

            response.put("orderFound", true);
            response.put("orderCompleted", true);
        }

        return response;
    }

    public Optional<OrdersModel> OrdersFinder(int id){
        Optional<OrdersModel> FindOrder = orderRepository.findById(id);
        return FindOrder;
    }
    
}
