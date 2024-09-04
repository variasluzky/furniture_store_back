package com.ltp.furniture_store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private Integer cartId;
    private Integer customerId;
    private Date createdAt;
    private Date updatedAt;
    private String statusDescription;
}
