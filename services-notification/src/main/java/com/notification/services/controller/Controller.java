package com.notification.services.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class Controller {

    private String message = "Benvenuto nel Api!!";

    @GetMapping("/welcome")
    public ResponseEntity<HashMap<String, Object>> getWelcome() {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("message", message);
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(maps, HttpStatus.OK);
    }
    
}
