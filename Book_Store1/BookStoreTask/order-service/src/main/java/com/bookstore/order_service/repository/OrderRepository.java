package com.bookstore.order_service.repository;

import com.bookstore.order_service.dataModel.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
