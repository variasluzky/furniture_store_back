package com.ltp.furniture_store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String address;
    private Double totalPrice;
    private String status;
    private List<OrderItemDTO> orderItems;
}
