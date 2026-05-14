package com.users.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.book.models.BookModel;
import com.users.book.repository.BookRepositery;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api-test")
public class ProvaController {
    private String messaggio = "Ciao a tutti, benvenuti in UserApi";

    @Autowired
    private BookRepositery books;

    @GetMapping("/welcome")
    public ResponseEntity<String> testWelcome(){
        return new ResponseEntity<String>(messaggio, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<List<BookModel>> getTestApi() {
        List<BookModel> booksList = books.findAll();
        return new ResponseEntity<List<BookModel>>(booksList, HttpStatus.OK);
    }
    
}
