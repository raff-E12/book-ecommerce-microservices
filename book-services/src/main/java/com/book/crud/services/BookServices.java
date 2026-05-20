package com.book.crud.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.crud.repository.*;
import com.book.crud.dto.Book;
import com.book.crud.mapper.*;
import com.book.crud.model.*;

@Service
public class BookServices {
    
    @Autowired
    private BookRepositery books;

    @Autowired
    private BookMapper bookMapper;

    public List<Book> ListBookAll(){
        List<BookModel> BooksList = books.findAllNative();
        return bookMapper.toDtoList(BooksList);
    }

    public Optional<Book> ListBookID(int id){
        Optional<BookModel> BooksOptional = books.findById(id);
        Optional<Book> bookDtoOptional = BooksOptional.map(bookModel -> bookMapper.toDto(bookModel)); // Usa map per trasformare l'Optional<BookModel> in Optional<Book>
        return bookDtoOptional;
    }

    public void AddBook(BookModel book){
        books.save(book);
    }

    public void DeleteBook(int id){
        books.deleteById(id);
    }

    public boolean ContainBook(int id){
        return books.existsById(id);
    }

    public void UpdateBook(BookModel book){
        books.save(book);
    }

    public void UpdateTrash(int id){
        books.trashById(id);
    }

    public void RestoreTrash(int id){
        books.restoreById(id);
    }

    public List<Book> TrashListBooks(boolean set){
        if(!set){
            List<BookModel> lists = books.findAllByTrashedFalse();
            return bookMapper.toDtoList(lists);
        }

        List<BookModel> lists = books.findAllByTrashedTrue();
        return bookMapper.toDtoList(lists);
    }
}
