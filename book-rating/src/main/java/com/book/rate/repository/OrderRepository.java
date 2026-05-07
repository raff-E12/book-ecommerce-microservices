package com.book.rate.repository;
import com.book.rate.models.OrdersModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<OrdersModel, Integer> {
    
}
