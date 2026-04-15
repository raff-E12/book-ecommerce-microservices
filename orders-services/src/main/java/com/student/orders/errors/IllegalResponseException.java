package com.student.orders.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalResponseException extends IllegalArgumentException{
    public IllegalResponseException(String message) {
        super(message);
    }    
}
