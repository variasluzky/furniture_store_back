package com.ltp.furniture_store.repository;

import com.ltp.furniture_store.entity.OrderItem;
import com.ltp.furniture_store.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    @Query("SELECT MAX(oi.id.orderItemId) FROM OrderItem oi WHERE oi.id.orderId = :orderId")
    Integer findMaxOrderItemIdByOrderId(@Param("orderId") Integer orderId);
}
