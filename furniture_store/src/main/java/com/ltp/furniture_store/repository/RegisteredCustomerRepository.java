package com.ltp.furniture_store.repository;
import com.ltp.furniture_store.entity.RegisteredCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface RegisteredCustomerRepository extends JpaRepository<RegisteredCustomer, Integer> {
    }

