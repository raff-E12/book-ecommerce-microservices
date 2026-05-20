package com.book.rate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.book.rate.global.AccessResponse;
import com.book.rate.global.RegisterResponse;
import com.book.rate.models.UserModel;
import com.book.rate.services.UserServices;

import java.util.Map;

@RestController
@RequestMapping("/api-user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterResponse registerRequest) {
        Map<String, Object> response = userServices.registerUser(registerRequest);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AccessResponse accessRequest) {
        Map<String, Object> response = userServices.loginUser(accessRequest);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = userServices.getAllUsers();
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Integer id) {
        Map<String, Object> response = userServices.getUserById(id);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody UserModel updatedUser) {
        Map<String, Object> response = userServices.updateUser(id, updatedUser);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = userServices.deleteUser(id);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }
}
