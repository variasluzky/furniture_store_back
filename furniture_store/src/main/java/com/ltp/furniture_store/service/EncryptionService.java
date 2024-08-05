package com.ltp.furniture_store.service;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final AES256TextEncryptor encryptor;

    public EncryptionService(@Value("${jasypt.encryptor.password}") String encryptionKey) {
        encryptor = new AES256TextEncryptor();
        encryptor.setPassword(encryptionKey);
    }

    public String encrypt(String data) {
        return encryptor.encrypt(data);
    }

    public String decrypt(String encryptedData) {
        return encryptor.decrypt(encryptedData);
    }
}

