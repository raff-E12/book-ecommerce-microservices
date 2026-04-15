package com.book.crud.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.crud.model.BookModel;

// Aggiunta della Repositery
@Repository
public interface BookRepositery extends JpaRepository<BookModel, Integer>  {
    List<BookModel> findAllById(Integer id);
}
