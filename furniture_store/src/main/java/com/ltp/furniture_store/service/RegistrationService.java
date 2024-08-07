package com.ltp.furniture_store.service;
import com.ltp.furniture_store.entity.RegisteredCustomer;
import com.ltp.furniture_store.repository.RegisteredCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class RegistrationService {

        @Autowired
        private RegisteredCustomerRepository registeredCustomerRepository;

        public RegisteredCustomer registerUser(RegisteredCustomer registeredCustomer) {
            return registeredCustomerRepository.save(registeredCustomer);
        }
    }


