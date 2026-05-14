package com.users.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.users.book.models.OrdersModel;

@Repository
public interface OrderRepository extends JpaRepository<OrdersModel, Integer> {
    
}
