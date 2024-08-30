package com.ltp.furniture_store.repository;

import com.ltp.furniture_store.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
