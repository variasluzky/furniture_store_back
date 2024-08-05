package com.ltp.furniture_store.repository;


import com.ltp.furniture_store.entity.ShoppingCartItem;
import com.ltp.furniture_store.entity.ShoppingCartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartItemRepository  extends JpaRepository<ShoppingCartItem, ShoppingCartItemId> {
    @Query("SELECT MAX(oi.id.cartItemId) FROM ShoppingCartItem oi WHERE oi.id.cartId = :cartId")
    Integer findMaxShoppingCartIdByCartId(@Param("cartId") Integer cartId);
}
