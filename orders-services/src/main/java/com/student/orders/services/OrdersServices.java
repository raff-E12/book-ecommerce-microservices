package com.student.orders.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.orders.dto.Book;
import com.student.orders.dto.BookTableList;
import com.student.orders.dto.Checkout;
import com.student.orders.errors.IllegalResponseException;
import com.student.orders.global.CreateOrder;
import com.student.orders.mappers.MappersGlobals;
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
    private MappersGlobals mappersGlobal;

    public List<Checkout> getAllOrders(int ordineId) {
        List<Checkout> checkList = checkRepository.findAllConTitolo(ordineId);
        return checkList;
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

    public boolean createOrder(CreateOrder orders) {
        OrdersModel order = new OrdersModel();
        List<CheckOutModel> check = new ArrayList<>();

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
        }

        if (check != null && !check.isEmpty()) {
            order.setRigheCheckout(check);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public boolean checkOrderExists(List<BookTableList> prod) {
        for (BookTableList item : prod) {
            if (checkRepository.findByIdBook(item.libro_id().intValue()) == null) {
                return false;
            }
        }
        return true;
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
            response.put("orderFound", true);
            response.put("orderCompleted", true);
        }

        return response;
    }
    
}
