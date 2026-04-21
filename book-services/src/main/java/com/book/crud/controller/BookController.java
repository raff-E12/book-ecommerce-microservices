package com.book.crud.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.crud.dto.Book;
import com.book.crud.errors.DataInsertException;
import com.book.crud.errors.InvalidDateException;
import com.book.crud.errors.ResourceNotFoundException;
import com.book.crud.mapper.BookMapper;
import com.book.crud.model.*;
import com.book.crud.services.BookServices;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RefreshScope
@RateLimiter(name = "BookEndpoint", fallbackMethod = "FallBackBook")
public class BookController {

    @Autowired
    private BookServices books;
    
    @Autowired
    private BookMapper bookMapper;

    public ResponseEntity<HashMap<String, Object>> FallBackBook(RequestNotPermitted ex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("message", "Troppe richieste, riprova tra poco");
        maps.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        return new ResponseEntity<>(maps, HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/libri")
	public ResponseEntity<HashMap<String, Object>> FindAll(){
        HashMap<String, Object> maps = new HashMap<>();
        List<Book> response = books.ListBookAll();

        if(response.isEmpty()){
            throw new ResourceNotFoundException("Lista Vuota");
        }

        maps.put("message", "Lista Completa");
        maps.put("books", response);
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
	}

	@GetMapping("/libri/{id}")
	public ResponseEntity<HashMap<String, Object>> FindIdBooks(@PathVariable int id){
        HashMap<String, Object> maps = new HashMap<>();
        Optional<Book> bookFind = books.ListBookID(id);

        if(bookFind.isEmpty()){
            throw new ResourceNotFoundException("Elemento Non Trovato");
        }

        maps.put("message", "Elemento Trovato");
        maps.put("books", bookFind);
        maps.put("id", id);
        maps.put("status", HttpStatus.OK.value());

        return new ResponseEntity<>(maps, HttpStatus.OK);
	}

    @PostMapping("/libri")
	public ResponseEntity<HashMap<String, Object>> AddBook(@RequestBody BookModel book){
        HashMap<String, Object> maps = new HashMap<>();
        int annoAttuale = LocalDate.now().getYear();

        if (book.getAnnoPubblicazione() < 1000 || book.getAnnoPubblicazione() > annoAttuale) {
            throw new InvalidDateException();
        }
        
        if (book.getAutore().isEmpty() || book.getCategoria().isEmpty() || book.getTitolo().isEmpty() || book.getIsbn().isEmpty() || book.getDisponibile() == 0) {
            HashMap<String, Boolean> required = new HashMap<>();
            
            if(book.getAutore().isEmpty()){
                required.put("autore", true); 
            } 
            
            if(book.getCategoria().isEmpty()){
                required.put("categoria", true);
            } 
            
            if(book.getTitolo().isEmpty()) {
                required.put("titolo", true);
            } 
            
            if(book.getIsbn().isEmpty()){
                required.put("isbn", true);
            } 
            
            if(book.getDisponibile() == 0){
                required.put("disponibile", true);
            }

            throw new DataInsertException("Alcuni Campi Obbligatori Vanno Compilati", required);
        }

        books.AddBook(book);
        maps.put("message", "Il Libro è Aggiunto con Successo");
        maps.put("status", HttpStatus.CREATED.value());
        return new ResponseEntity<>(maps, HttpStatus.CREATED);
	}

    @DeleteMapping("/libri/{id}")
    public ResponseEntity<HashMap<String, Object>> DeleteBook(@PathVariable int id){
        HashMap<String, Object> maps = new HashMap<>();
        boolean isValid = books.ContainBook(id);

        if (isValid) {
            books.DeleteBook(id);
            maps.put("message", "Libro Rimosso");
            maps.put("status", HttpStatus.OK);
            return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
        }

        maps.put("message", "Libro non esiste!!");
        maps.put("status", HttpStatus.NOT_FOUND.value());

       return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/libri/{id}")
    public ResponseEntity<HashMap<String, Object>> BookUpdateFind(@PathVariable int id, @RequestBody BookModel book) {
        HashMap<String, Object> maps = new HashMap<>();
        boolean isValid = books.ContainBook(id); 
        
        if (!isValid) {
            maps.put("message", "Libro non Esiste!!");
            maps.put("status", HttpStatus.NOT_FOUND.value());
           return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.NOT_FOUND);
        }

        Optional<Book> FindBook = books.ListBookID(id);
        BookModel existing = bookMapper.toEntity(FindBook.get()); // Trasforma il DTO in Entity per poterlo modificare

        if (book.getAutore() != null && !book.getAutore().isBlank())
            existing.setAutore(book.getAutore());

        if (book.getTitolo() != null && !book.getTitolo().isBlank())
            existing.setTitolo(book.getTitolo());

        if (book.getCategoria() != null && !book.getCategoria().isBlank())
            existing.setCategoria(book.getCategoria());

        if (book.getEditore() != null && !book.getEditore().isBlank())
            existing.setEditore(book.getEditore());

        if (book.getNote() != null && !book.getNote().isBlank())
            existing.setNote(book.getNote());

        if (book.getPosizioneScaffale() != null && !book.getPosizioneScaffale().isBlank())
            existing.setPosizioneScaffale(book.getPosizioneScaffale());

        if (book.getIsbn() != null && !book.getIsbn().isBlank())
            existing.setIsbn(book.getIsbn());

        if (book.getDisponibile() != null)
            existing.setDisponibile(book.getDisponibile());

        if (book.getAnnoPubblicazione() != null)
            existing.setAnnoPubblicazione(book.getAnnoPubblicazione());

        if (book.getNumCopie() != null)
            existing.setNumCopie(book.getNumCopie());

        if (book.getCoverColor() != null && !book.getCoverColor().isBlank())
            existing.setCoverColor(book.getCoverColor());

        books.UpdateBook(existing);
        maps.put("message", "Libro è stato aggiornato!!");
        maps.put("status", HttpStatus.OK.value());
        return new ResponseEntity<HashMap<String, Object>>(maps, HttpStatus.OK);
    }

}
