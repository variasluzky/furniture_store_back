package com.ltp.furniture_store.web;

import com.ltp.furniture_store.entity.Order;
import com.ltp.furniture_store.entity.OrderDTO;
import com.ltp.furniture_store.entity.OrderRequest;
import com.ltp.furniture_store.entity.OrderResponse;
import com.ltp.furniture_store.service.OrderService;
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
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-address")
    public ResponseEntity<Order> updateOrderAddress(@RequestParam Integer orderId, @RequestParam String newAddress, @RequestParam Integer userId) {
        Order updatedOrder = orderService.updateOrderAddress(orderId, newAddress, userId);
        return ResponseEntity.ok(updatedOrder);
    }
}
