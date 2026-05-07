package com.book.rate.repository;

import com.book.rate.models.RateModel;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RateRepositery extends JpaRepository<RateModel, Integer> {
    List<RateModel> findByLibroId(Integer libroId);
}
