package com.ltp.furniture_store.web;

import com.ltp.furniture_store.entity.Payment;
import com.ltp.furniture_store.entity.PaymentRequest;
import com.ltp.furniture_store.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            System.out.println("Received payment request: " + paymentRequest);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth creditCardExpDate = YearMonth.parse(paymentRequest.getCreditCardExpDate(), formatter);

            Payment payment = paymentService.processPayment(
                    paymentRequest.getUserId(),
                    paymentRequest.getOrderId(),
                    paymentRequest.getCardholderId(),
                    paymentRequest.getCardholderFirstName(),
                    paymentRequest.getCardholderLastName(),
                    paymentRequest.getCreditCardNumber(),
                    creditCardExpDate,
                    paymentRequest.getCvv()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
