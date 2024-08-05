package com.ltp.furniture_store.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ShoppingCartItemId implements Serializable {
    private Integer cartId;
    private Integer cartItemId;
}
