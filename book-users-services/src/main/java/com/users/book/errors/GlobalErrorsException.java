package com.users.book.errors;

import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorsException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorGlobal> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorGlobal error = new ErrorGlobal();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessagge(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(DataInsertException.class)
    public ResponseEntity<ErrorGlobal> handleElementNotFound(DataInsertException ex) {
        ErrorGlobal error = new ErrorGlobal();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessagge(ex.getMessage());
        error.setRequired(ex.getRequired());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorGlobal> handleInvalidDate(DataIntegrityViolationException ex) {
        ErrorGlobal error = new ErrorGlobal();
         System.out.println(ex.getMessage().contains("anno_pubblicazione"));
        if (ex.getMessage().contains("anno_pubblicazione")) {
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setMessagge("Anno di pubblicazione non valido. " +
                              "Deve essere compreso tra 1000 e " + 
                              LocalDate.now().getYear());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorGlobal> handleInvalidDate(InvalidDateException ex) {
        ErrorGlobal error = new ErrorGlobal();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessagge(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}