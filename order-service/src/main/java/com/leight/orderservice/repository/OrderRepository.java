package com.leight.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leight.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
