package com.ltp.furniture_store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal promotion;
    private Long productId;
}
