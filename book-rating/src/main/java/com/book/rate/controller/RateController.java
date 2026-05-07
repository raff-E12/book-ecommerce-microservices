package com.book.rate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.book.rate.dto.CreateComments;
import com.book.rate.dto.Rate;
import com.book.rate.errors.*;
import com.book.rate.models.BookModel;
import com.book.rate.models.RateModel;
import com.book.rate.repository.BookRepositery;
import com.book.rate.services.RateServices;
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

    @PostMapping("/create-comment")
    public ResponseEntity<HashMap<String, Object>> postMethodName(@RequestBody CreateComments comments) {
        HashMap<String, Object> maps = new HashMap<>();
        RateModel rateModel = new RateModel();
        BookModel book = bookRepositery.findById(comments.BookId())
        .orElseThrow(() -> new ResourceNotFoundException("Libro non trovato con id: " + comments.BookId()));
        
        rateModel.setLibro(book);
        rateModel.setDescrizione(comments.Description());
        rateModel.setVoto(comments.Rating());
        rateModel.setChecked(false);
        Rate rate = rateServices.createComment(rateModel);

        if(rate == null) {
            throw new BadRequestException("Errore durante la creazione del commento");

        }

        maps.put("message", "Commento creato con successo");
        maps.put("status", 201);
        return ResponseEntity.status(201).body(maps);
    }
    
}
