package com.ltp.furniture_store.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_card_detail")
public class CreditCardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private RegisteredCustomer registeredCustomer;

    @Value("${jasypt.encryptor.password}")
    private String encryptionKey;

    private AES256TextEncryptor encryptor;

    @PostConstruct
    public void init() {
        encryptor = new AES256TextEncryptor();
        encryptor.setPassword(encryptionKey);
    }

    @NonNull
    @Column(name = "cardholder_id", nullable = false)
    private String encryptedCardholderId;

    @NonNull
    @Column(name = "credit_card_number", nullable = false)
    private String encryptedCreditCardNumber;

    @NonNull
    @Column(name = "cardholder_first_name", nullable = false)
    private String cardholderFirstName;

    @NonNull
    @Column(name = "cardholder_last_name")
    private String cardholderLastName;

    @NonNull
    @Column(name = "credit_card_exp_date", nullable = false)
    private YearMonth creditCardExpDate;

    @Column (name = "cvv",nullable = false)
    private String encryptedCvv;

    // Custom constructor with encryption
    public CreditCardDetail(RegisteredCustomer registeredCustomer, String cardholderId, String creditCardNumber,
                            String cardholderFirstName, String cardholderLastName, YearMonth creditCardExpDate, String cvv) {
        this.registeredCustomer = registeredCustomer;
        this.cardholderFirstName = cardholderFirstName;
        this.cardholderLastName = cardholderLastName;
        this.creditCardExpDate = creditCardExpDate;

        // Encrypt sensitive data
        this.encryptedCardholderId = encryptor.encrypt(cardholderId);
        this.encryptedCreditCardNumber = encryptor.encrypt(creditCardNumber);
        this.encryptedCvv = encryptor.encrypt(cvv);
    }

    // Getters and setters for encrypted fields

    public void setCvv(String cvv) {
        this.encryptedCvv = encryptor.encrypt(cvv);
    }

    public String getCvv() {
        return encryptor.decrypt(this.encryptedCvv);
    }
    public void setCardholderId(String cardholderId) {
        this.encryptedCardholderId = encryptor.encrypt(cardholderId);
    }

    public String getCardholderId() {
        return encryptor.decrypt(this.encryptedCardholderId);
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.encryptedCreditCardNumber = encryptor.encrypt(creditCardNumber);
    }

    public String getCreditCardNumber() {
        return encryptor.decrypt(this.encryptedCreditCardNumber);
    }

}
