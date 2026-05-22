package com.student.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.orders.components.OrderKafka;
import com.student.orders.dto.CheckComplete;
import com.student.orders.dto.Checkout;
import com.student.orders.errors.IllegalResponseException;
import com.student.orders.errors.ResourceNotFoundException;
import com.student.orders.events.OrderCheck;
import com.student.orders.global.CreateOrder;
import com.student.orders.global.interfaces.CheckOutQuery;
import com.student.orders.model.OrdersModel;
import com.student.orders.services.OrdersServices;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
@RefreshScope
@RateLimiter(name ="OrderEndpoint", fallbackMethod = "FallBackOrder")
public class OdersController {
    
    @Autowired
    private OrdersServices ordersServices;

    public ResponseEntity<HashMap<String, Object>> FallBackOrder(RequestNotPermitted ex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("message", "Troppe richieste, riprova tra poco");
        maps.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        return new ResponseEntity<>(maps, HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<HashMap<String, Object>> getMethodName(@PathVariable int id) {
        HashMap<String, Object> maps = new HashMap<>();
        List<Checkout> response = ordersServices.getAllOrders(id);

        if (response.isEmpty()) {
            throw new ResourceNotFoundException("Non sono presenti ordini");
        }

        maps.put("message", "Lista Completa");
        maps.put("checkout", response);
        maps.put("status", HttpStatus.OK.value());
        maps.put("length", response.size());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }
    

    @DeleteMapping("/delete-prod/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteOrder(@PathVariable int id) {
        HashMap<String, Object> maps = new HashMap<>();
        boolean response = ordersServices.deleteProd(id);

        if (!response) {
            throw new ResourceNotFoundException("Prodotto non trovato");
        }

        maps.put("message", "Prodotto eliminato con successo");
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

    @DeleteMapping("/delete-all/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteAllOrder(@PathVariable int id) {
        HashMap<String, Object> maps = new HashMap<>();
        boolean response = ordersServices.deleteOrder(id);

        if (!response) {
            throw new ResourceNotFoundException("Ordine non trovato");
        }

        maps.put("message", "Ordine eliminato con successo");
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

    @PostMapping("/create-order")
    public ResponseEntity<HashMap<String, Object>> postMethodName(@RequestBody CreateOrder orders) {
        HashMap<String, Object> maps = new HashMap<>();

        if (!ordersServices.checkOrderExists(orders.getShop())) {
            throw new ResourceNotFoundException("Uno o più prodotti non trovati");
        }

        HashMap<String, Object> response = ordersServices.createOrder(orders);
        boolean status = Boolean.parseBoolean(response.get("status").toString());
        if (!status) {
            throw new IllegalResponseException("Errore nella creazione dell'ordine");
        }

        Integer id = Integer.parseInt(response.get("id").toString());

        maps.put("message", "Ordine creato con successo");
        maps.put("feedback", response);
        maps.put("status", HttpStatus.OK.value());
        maps.put("IdOrder", id);

        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

    @PatchMapping("/check-order/{id}")
    public ResponseEntity<HashMap<String, Object>> CheckOrderBuying(@PathVariable String id) {
        HashMap<String, Object> maps = new HashMap<>();
        Map<String, Boolean> response = ordersServices.checkOrderAfterBuy(Integer.parseInt(id));

        if (!response.get("orderFound")) {
             throw new ResourceNotFoundException("Ordine non trovato");
        } 
        
        if (!response.get("orderCompleted")) {
             throw new ResourceNotFoundException("L'Ordine è stato gia completato");
        }

        maps.put("message", "Ordine completato con successo");
        maps.put("feedback", response);
        maps.put("status", HttpStatus.OK.value());

        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<HashMap<String, Object>> getListOrders() {
        HashMap<String, Object> maps = new HashMap<>();
        List<CheckComplete> list = ordersServices.orderInfoAll();
        
        if(list.isEmpty()){
            throw new ResourceNotFoundException("Ordini non trovati");
        }

        maps.put("message", "Ordini trovati con successo");
        maps.put("Orders", list);
        maps.put("size", list.size());
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

}
