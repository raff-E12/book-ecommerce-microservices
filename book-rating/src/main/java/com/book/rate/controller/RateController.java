package com.book.rate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.book.rate.dto.CreateComments;
import com.book.rate.dto.Rate;
import com.book.rate.errors.*;
import com.book.rate.models.BookModel;
import com.book.rate.models.RateModel;
import com.book.rate.models.UserModel;
import com.book.rate.repository.BookRepositery;
import com.book.rate.repository.UserRepositery;
import com.book.rate.services.RateServices;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class RateController {

    @Autowired
    private RateServices rateServices;

    @Autowired
    private BookRepositery bookRepositery;

    @Autowired
    private UserRepositery userRepositery;

    @GetMapping("/get-comments")
    public ResponseEntity<HashMap<String, Object>> getCommentsAll() {
        HashMap<String, Object> maps = new HashMap<>();
        List<Rate> rate = rateServices.getAllComments();

        if (!rate.isEmpty()) {
            maps.put("message", "Commenti trovati");
            maps.put("comments", rate);
            maps.put("status", 200);
        } else {
            throw new ResourceNotFoundException("Nessun commento trovato");
        }

        return ResponseEntity.ok(maps);
    }
    
    @GetMapping("/get-comment/{id}")
    public ResponseEntity<HashMap<String, Object>> getComments(@PathVariable Integer id) {
        HashMap<String, Object> maps = new HashMap<>();
        
        Optional<Rate> rate = rateServices.getRateById(id);
        if (rate.isPresent()) {
            maps.put("message", "Commento trovato");
            maps.put("rates", rate.get());
            maps.put("status", 200);
        } else {
            throw new ResourceNotFoundException("Risorsa non trovata con id: " + id);
        }

        return ResponseEntity.ok(maps);
    }

    @GetMapping("/book-comment/{id}")
    public ResponseEntity<HashMap<String, Object>> getBookComments(@PathVariable Integer id) {
        HashMap<String, Object> maps = new HashMap<>();

        List<Rate> rate = rateServices.getBookComments(id);
        if (!rate.isEmpty()) {
            maps.put("message", "Commenti trovati");
            maps.put("comments", rate);
            maps.put("status", 200);
        } else {
            throw new ResourceNotFoundException("Risorsa non trovata con id: " + id);
        }

        return ResponseEntity.ok(maps);
    }

    @GetMapping("/user-comment/{id}")
    public ResponseEntity<HashMap<String, Object>> getUserComments(@PathVariable Integer id) {
        HashMap<String, Object> maps = new HashMap<>();

        List<Rate> rate = rateServices.getUserComments(id);
        if (!rate.isEmpty()) {
            maps.put("message", "Commenti utente trovati");
            maps.put("comments", rate);
            maps.put("status", 200);
        } else {
            throw new ResourceNotFoundException("Nessun commento trovato per utente con id: " + id);
        }

        return ResponseEntity.ok(maps);
    }

    @PostMapping("/create-comment")
    public ResponseEntity<HashMap<String, Object>> CreateComments(@RequestBody CreateComments comments) {
        HashMap<String, Object> maps = new HashMap<>();
        RateModel rateModel = new RateModel();
        BookModel book = bookRepositery.findById(comments.BookId())
            .orElseThrow(() -> new ResourceNotFoundException("Libro non trovato con id: " + comments.BookId()));

        UserModel user = userRepositery.findById(comments.UserId())
            .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + comments.UserId()));

        rateModel.setLibro(book);
        rateModel.setUtente(user);
        rateModel.setDescrizione(comments.Description());
        rateModel.setVoto(comments.Rating());
        rateModel.setChecked(false);
        Rate rate = rateServices.createComment(rateModel);

        if(rate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creazione non Andata a Buon Fine");
        }

        maps.put("message", "Commento creato con successo");
        maps.put("status", 201);
        return ResponseEntity.status(201).body(maps);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HashMap<String, Object>> DeleteComments(@PathVariable Integer id){
        HashMap<String, Object> maps = new HashMap<>();
        Boolean DeleteComment = rateServices.getRateDelete(id);

        if(!DeleteComment){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Richiesta non andata a buon fine, Riprova!!");
        }

        maps.put("message", "Commento Eliminato con successo");
        maps.put("status", 200);
        maps.put("feedback", DeleteComment);
        return ResponseEntity.status(200).body(maps);
    }

}
