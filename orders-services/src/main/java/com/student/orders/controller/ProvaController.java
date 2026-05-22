package com.student.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.student.orders.dto.Checkout;
import com.student.orders.services.OrdersServices;

@RestController
@RequestMapping("/api-test")
@RefreshScope
public class ProvaController {
	
	@Autowired
    private OrdersServices services;
	private String messaggio = "Ciao a tutti, benvenuti in OrdersDespenceApi";

	@GetMapping("/welcome")
	public ResponseEntity<String> testWelcome(){
		return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
	}
	
	@GetMapping("/db-test/{id}")
	public ResponseEntity<List<Checkout>> FindSingle(@PathVariable int id){
        return new ResponseEntity<>(services.getAllOrders(id), HttpStatus.OK);
	}

}
