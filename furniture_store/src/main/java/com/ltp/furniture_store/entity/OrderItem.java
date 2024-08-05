package com.ltp.furniture_store.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("orderId")  // This maps the orderId attribute of OrderItemId to the Order entity
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Catalog catalog;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

}

