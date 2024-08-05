package com.ltp.furniture_store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {

    @EmbeddedId
    private ShoppingCartItemId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id",nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Catalog catalog;

    @Column(name = "quantity_cart",nullable = false)
    private Integer quantityCart;
}
