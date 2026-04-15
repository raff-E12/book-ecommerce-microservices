package com.student.orders.repository;

import com.student.orders.model.CheckOutModel;
import com.student.orders.model.OrdersModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrdersModel, Integer> {
    
}
