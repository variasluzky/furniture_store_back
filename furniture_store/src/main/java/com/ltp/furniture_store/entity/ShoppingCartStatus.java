package com.ltp.furniture_store.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart_status")
public class ShoppingCartStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_cart")
    private Short statusCart;

    @NonNull
    @Column(name = "status_description", nullable = false)
    private String statusDescription;

}