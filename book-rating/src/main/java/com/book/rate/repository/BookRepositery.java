package com.book.rate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.rate.models.BookModel;

@Repository
public interface BookRepositery extends JpaRepository<BookModel, Integer>  {
    List<BookModel> findAllById(Integer id);

    @Query("SELECT b.id FROM BookModel b WHERE b.id IN :id")
    List<Integer> findExistingIds(@Param("id") List<Integer> id);
}
