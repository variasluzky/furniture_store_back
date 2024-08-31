package com.ltp.furniture_store.web;

import com.ltp.furniture_store.entity.*;
import com.ltp.furniture_store.service.OrderService;
import com.ltp.furniture_store.service.RegisteredCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RegisteredCustomerService registeredCustomerService;


    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Received OrderRequest: " + orderRequest);

        try {
            OrderResponse orderResponse = orderService.createOrderFromCart(
                    orderRequest.getCustomerId(),
                    orderRequest.getTotalPrice(),
                    orderRequest.isDelivery(),
                    orderRequest.getAddress(),
                    orderRequest.getItems()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
        } catch (RuntimeException e) {
            System.err.println("Error creating order: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrders(@PathVariable Integer userId, @RequestParam boolean isAdmin) {
        System.out.println("Received userId: " + userId);
        System.out.println("Received isAdmin: " + isAdmin);

        RegisteredCustomer user = registeredCustomerService.findUserById(userId);
        List<OrderDTO> orders = orderService.getOrdersByUserIdOrAll(userId, isAdmin);
        return ResponseEntity.ok(orders);
    }



    @PutMapping("/update-address")
    public ResponseEntity<Order> updateOrderAddress(@RequestParam Integer orderId, @RequestParam String newAddress, @RequestParam Integer userId) {
        Order updatedOrder = orderService.updateOrderAddress(orderId, newAddress, userId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestParam Integer orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order successfully marked as canceled");
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItems(@PathVariable Integer orderId) {
        List<OrderItemDTO> orderItems = orderService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/complete-order")
    public ResponseEntity<String> completeOrder(@RequestParam Integer orderId, @RequestParam Integer adminUserId) {
        return orderService.updateOrderStatusToCompleted(orderId, adminUserId);
    }

}
