package com.ltp.furniture_store.web;
import com.ltp.furniture_store.exception.IncorrectPasswordException;
import com.ltp.furniture_store.exception.UserNotFoundException;
import com.ltp.furniture_store.entity.PermissionType;
import com.ltp.furniture_store.entity.RegisteredCustomer;
import com.ltp.furniture_store.entity.RegistrationDTO;
import com.ltp.furniture_store.entity.UserDTO;
import com.ltp.furniture_store.repository.PermissionTypeRepository;
import com.ltp.furniture_store.service.RegisteredCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RegisteredCustomerController {

    @Autowired
    private RegisteredCustomerService registrationService;

    @Autowired
    private PermissionTypeRepository permissionTypeRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {

        PermissionType defaultPermission = permissionTypeRepository.findByPermissionStatus("user");
        RegisteredCustomer customer = new RegisteredCustomer(
                registrationDTO.getFirstName(),
                registrationDTO.getLastName(),
                registrationDTO.getPhone(),
                registrationDTO.getEmail(),
                registrationDTO.getPassword(),
                defaultPermission
        );
        customer = registrationService.registerUser(customer);
        return ResponseEntity.ok(customer);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            RegisteredCustomer customer = registrationService.findUserById(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logInUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            RegisteredCustomer customer = registrationService.findUserByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

            if (!customer.getPassword().equals(password)) {
                throw new IncorrectPasswordException("Password is incorrect for email: " + email);
            }


            UserDTO userDTO = new UserDTO(
                    customer.getCustomerId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getPermissions().getPermissionStatus()
            );


            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/clients")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
        List<RegisteredCustomer> customers = registrationService.getAllCustomers();
        List<UserDTO> customerDTOs = customers.stream().map(customer -> new UserDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPermissions().getPermissionStatus()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(customerDTOs);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Integer id, @RequestBody Map<String, String> updates) {
        try {
            RegisteredCustomer customer = registrationService.findUserById(id);
            if (updates.containsKey("firstName")) {
                customer.setFirstName(updates.get("firstName"));
            }
            if (updates.containsKey("lastName")) {
                customer.setLastName(updates.get("lastName"));
            }
            if (updates.containsKey("phone")) {
                customer.setPhone(updates.get("phone"));
            }
            customer.setUpdatedAt(new Date());
            registrationService.save(customer);
            // Return JSON response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User details updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user details: " + e.getMessage());
        }
    }



}


