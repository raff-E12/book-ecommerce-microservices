package com.student.orders.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.orders.model.BooksModels;

@Repository
public interface BookRepositery extends JpaRepository<BooksModels, Integer>  {
    List<BooksModels> findAllById(Integer id);
}
