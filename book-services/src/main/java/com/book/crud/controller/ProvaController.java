package com.book.crud.controller;

import com.book.crud.dto.Book;
import com.book.crud.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.book.crud.services.BookServices;

// Controller di veri propri Test
@RestController
@RequestMapping("/api-test")
@RefreshScope
public class ProvaController {

    @Autowired
    private BookServices books;
	private String messaggio = "Ciao a tutti, benvenuti in BookDespenceApi";

	@GetMapping("/welcome")
	public ResponseEntity<String> testWelcome(){
		return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
	}
	
	@GetMapping("/db-test")
	public ResponseEntity<List<Book>> FindAll(){
        return new ResponseEntity<>(books.ListBookAll(), HttpStatus.OK);
	}

	@GetMapping("/db-test/{id}")
	public ResponseEntity<Optional<Book>> FindIdBooks(@PathVariable int id){
        return new ResponseEntity<>(books.ListBookID(id), HttpStatus.OK);
	}
}
