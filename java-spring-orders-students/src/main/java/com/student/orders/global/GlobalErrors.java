package com.student.orders.global;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.student.orders.errors.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalErrors extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>(); 
        response.put( "error", "Not Found" ); 
        response.put( "message", ex.getMessage()); 
        response.put( "status", HttpStatus.NOT_FOUND.value()); 
        response.put( "timestamp", LocalDateTime.now()); 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex){
        Map<String, Object> response = new HashMap<>(); 
        response.put( "error", "Bad Request" ); 
        response.put( "message", ex.getMessage()); 
        response.put( "status", HttpStatus.BAD_REQUEST.value()); 
        response.put( "timestamp", LocalDateTime.now()); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
