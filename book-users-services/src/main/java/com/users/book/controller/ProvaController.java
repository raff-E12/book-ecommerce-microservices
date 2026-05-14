package com.users.book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-test")
public class ProvaController {
    private String messaggio = "Ciao a tutti, benvenuti in UserApi";

    @GetMapping("/welcome")
    public ResponseEntity<String> testWelcome(){
        return new ResponseEntity<String>(messaggio, HttpStatus.OK);
    }
}
