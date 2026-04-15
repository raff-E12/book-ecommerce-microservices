package com.student.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.orders.dto.Checkout;
import com.student.orders.errors.IllegalResponseException;
import com.student.orders.errors.ResourceNotFoundException;
import com.student.orders.global.CreateOrder;
import com.student.orders.model.OrdersModel;
import com.student.orders.services.OrdersServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class OdersController {
    
    @Autowired
    private OrdersServices ordersServices;

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

        if (ordersServices.checkOrderExists(orders.getShop())) {
            throw new ResourceNotFoundException("Uno o più prodotti non trovati");
        }

        boolean response = ordersServices.createOrder(orders);
        if (!response) {
            throw new IllegalResponseException("Errore nella creazione dell'ordine");
        }

        maps.put("message", "Ordine creato con successo");
        maps.put("feedback", response);
        maps.put("status", HttpStatus.OK.value());

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
    

}
