package com.ltp.furniture_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ltp.furniture_store.entity.RegisteredCustomer;

@Repository
public interface RegisteredCustomersRepository extends JpaRepository<RegisteredCustomer, Integer> {
    // Additional query methods can be defined here
}
