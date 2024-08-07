package com.ltp.furniture_store.controller;
import com.ltp.furniture_store.entity.RegisteredCustomer;
import com.ltp.furniture_store.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredCustomer> registerUser(@RequestBody RegisteredCustomer registeredCustomer) {
        RegisteredCustomer savedCustomer = registrationService.registerUser(registeredCustomer);
        return ResponseEntity.ok(savedCustomer);
    }
}

