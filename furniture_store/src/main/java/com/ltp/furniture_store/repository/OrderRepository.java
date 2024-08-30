package com.ltp.furniture_store.repository;

import com.ltp.furniture_store.entity.Order;
import com.ltp.furniture_store.entity.RegisteredCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findByCustomer(RegisteredCustomer customer);
}
