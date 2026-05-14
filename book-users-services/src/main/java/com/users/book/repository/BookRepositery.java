package com.users.book.repository;

import org.springframework.stereotype.Repository;
import com.users.book.models.BookModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BookRepositery extends JpaRepository<BookModel, Integer>{
    List<BookModel> findAllById(Integer id);

    @Query("SELECT b.id FROM BookModel b WHERE b.id IN :id")
    List<Integer> findExistingIds(@Param("id") List<Integer> id);
}
