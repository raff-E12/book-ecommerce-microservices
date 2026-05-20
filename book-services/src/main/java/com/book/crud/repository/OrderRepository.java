package com.book.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.crud.model.OrdineModel;

@Repository
public interface OrderRepository extends JpaRepository<OrdineModel, Integer> {}
