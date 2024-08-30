package com.ltp.furniture_store.service;

import com.ltp.furniture_store.entity.*;
import com.ltp.furniture_store.repository.CreditCardDetailRepository;
import com.ltp.furniture_store.repository.OrderRepository;
import com.ltp.furniture_store.repository.PaymentRepository;
import com.ltp.furniture_store.repository.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CreditCardDetailRepository creditCardDetailRepository;

    @Autowired
    private RegisteredCustomerService registeredCustomerService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    public Payment processPayment(Integer userId, Integer orderId, String cardholderId, String cardholderFirstName, String cardholderLastName, String creditCardNumber, YearMonth creditCardExpDate, String cvv) {

        RegisteredCustomer customer = registeredCustomerService.findUserById(userId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        CreditCardDetail creditCardDetail = new CreditCardDetail(customer, cardholderId, creditCardNumber, cardholderFirstName, cardholderLastName, creditCardExpDate, cvv, encryptionService);
        creditCardDetailRepository.save(creditCardDetail);

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setOrder(order);
        payment.setCreditCardDetail(creditCardDetail);
        payment.setPaymentDate(new Date());

        paymentRepository.save(payment);

        // Update the order status to "Paid"
        order.setStatus(statusRepository.findByDescriptionStatus(OrderStatusEnum.PAID));
        orderRepository.save(order);

        return payment;
    }
}
