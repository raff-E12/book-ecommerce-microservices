package com.book.rate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-test")
@RefreshScope
public class ProvaController {
    
    private String messaggio = "Ciao a tutti, benvenuti in RatingApi";

    @GetMapping("/welcome")
    public ResponseEntity<String> testWelcome(){
        return new ResponseEntity<String>(messaggio, HttpStatus.OK);
    }

}