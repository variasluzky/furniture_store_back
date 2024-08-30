package com.ltp.furniture_store.entity;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer userId;
    private Integer orderId;
    private String cardholderId;
    private String cardholderFirstName;
    private String cardholderLastName;
    private String creditCardNumber;
    private String creditCardExpDate;
    private String cvv;
}