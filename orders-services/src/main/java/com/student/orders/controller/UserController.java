package com.student.orders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.student.orders.model.*;
import com.student.orders.services.UserServices;
import com.student.orders.global.AccessResponse;
import com.student.orders.global.RegisterResponse;

import java.util.Map;

@RestController
@RequestMapping("/api-utenti")
public class UserController {

    @Autowired
    private UserServices userServices;

    // Registrazione utente
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterResponse registerRequest) {
        Map<String, Object> response = userServices.registerUser(registerRequest);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    // Accesso/Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AccessResponse accessRequest) {
        Map<String, Object> response = userServices.loginUser(accessRequest);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(response);
    }

    // Ottieni tutti gli utenti
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = userServices.getAllUsers();
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }

    // Ottieni utente per ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Integer id) {
        Map<String, Object> response = userServices.getUserById(id);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : (Boolean) response.get("error") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }

    // Aggiorna utente
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody UserModel updatedUser) {
        Map<String, Object> response = userServices.updateUser(id, updatedUser);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : (Boolean) response.get("error") ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }

    // Elimina utente
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = userServices.deleteUser(id);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : (Boolean) response.get("error") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }
}
