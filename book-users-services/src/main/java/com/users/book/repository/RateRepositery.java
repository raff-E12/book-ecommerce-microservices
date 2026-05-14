package com.users.book.repository;

import com.users.book.models.RateModel;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RateRepositery extends JpaRepository<RateModel, Integer> {
    List<RateModel> findByLibroId(Integer libroId);
}
