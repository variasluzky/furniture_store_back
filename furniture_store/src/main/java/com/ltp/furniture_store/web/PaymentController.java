package com.ltp.furniture_store.web;

import com.ltp.furniture_store.entity.Payment;
import com.ltp.furniture_store.entity.PaymentRequest;
import com.ltp.furniture_store.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;



    @PostMapping("/process")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            System.out.println("Received payment request: " + paymentRequest);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth creditCardExpDate = YearMonth.parse(paymentRequest.getCreditCardExpDate(), formatter);

            paymentService.processPayment(
                    paymentRequest.getUserId(),
                    paymentRequest.getOrderId(),
                    paymentRequest.getCardholderId(),
                    paymentRequest.getCardholderFirstName(),
                    paymentRequest.getCardholderLastName(),
                    paymentRequest.getCreditCardNumber(),
                    creditCardExpDate,
                    paymentRequest.getCvv()
            );

            // Just return OK
            return ResponseEntity.ok().build();

        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            System.err.println("Error processing payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
