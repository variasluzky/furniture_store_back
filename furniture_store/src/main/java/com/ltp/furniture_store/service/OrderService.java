package com.ltp.furniture_store.service;

import com.ltp.furniture_store.entity.*;
import com.ltp.furniture_store.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RegisteredCustomerService registeredCustomerService;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ShoppingCartStatusRepository shoppingCartStatusRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional
    public OrderResponse  createOrderFromCart(Integer customerId, double totalPrice, boolean delivery, String address, List<OrderItemRequest> items) {
        ShoppingCart cart = shoppingCartRepository.findByCustomerAndShoppingCartStatus_StatusDescription(
                        registeredCustomerService.findUserById(customerId), ShoppingCartStatusEnum.ACTIVE)
                .orElseThrow(() -> new RuntimeException("No active cart found for customer"));

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(totalPrice);
        order.setDate(new Date());
        order.setDelivery(delivery);
        order.setAddress(address);
        order.setStatus(statusRepository.findByDescriptionStatus(OrderStatusEnum.UNPAID));
        orderRepository.save(order);

        for (OrderItemRequest item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Catalog catalogItem = catalogRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setCatalog(catalogItem);
            orderItem.setQuantity(item.getQuantity());

            // Save the order item using OrderItemService
            orderItemService.saveOrderItem(orderItem);
        }

        cart.setShoppingCartStatus(shoppingCartStatusRepository.findByStatusDescription(ShoppingCartStatusEnum.COMPLETED));
        shoppingCartRepository.save(cart);

        return new OrderResponse(order.getOrderId(), order.getTotalPrice());
    }


    @Transactional
    public ResponseEntity<String> updateOrderAddress(Integer orderId, String newAddress, Integer userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Check if the user is authorized to update the address
        if (!order.getCustomer().getCustomerId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this order.");
        }

        // Fetch the PAID and UNPAID status from the database
        Status unpaidStatus = statusRepository.findByDescriptionStatus(OrderStatusEnum.UNPAID);
        Status paidStatus = statusRepository.findByDescriptionStatus(OrderStatusEnum.PAID);

        // Check if the order allows address updates
        if (Boolean.TRUE.equals(order.getDelivery()) &&
                (order.getStatus().getStatusId().equals(unpaidStatus.getStatusId()) ||
                        order.getStatus().getStatusId().equals(paidStatus.getStatusId()))) {

            // Update the address
            order.setAddress(newAddress);
            orderRepository.save(order);
            return ResponseEntity.ok("Address updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Address update is not allowed. The order status must be UNPAID or PAID and set for delivery.");
        }
    }


    @Transactional
    public List<OrderDTO> getOrdersByUserIdOrAll(Integer userId, boolean isAdmin) {
        List<Order> orders;

        if (isAdmin) {
            // Fetch all orders for admin
            orders = orderRepository.findAll();
        } else {
            // Fetch only the orders of the logged-in user
            RegisteredCustomer customer = registeredCustomerService.findUserById(userId);
            orders = orderRepository.findByCustomer(customer);
        }

        // Convert each Order to OrderDTO
        return orders.stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToOrderDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getAddress(),
                order.getTotalPrice(),
                order.getStatus().getDescriptionStatus().toString(),
                order.getDelivery(),
                order.getOrderItems().stream()
                        .map(this::convertToOrderItemDTO)
                        .collect(Collectors.toList())
        );
    }


    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getCatalog().getProductName(),
                orderItem.getQuantity(),
                orderItem.getCatalog().getPrice()
        );
    }

    @Transactional
    public ResponseEntity<String> cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Print the current status of the order
        System.out.println("Order Status before cancel attempt: " + order.getStatus().getDescriptionStatus());


        Status unpaidStatus = statusRepository.findByDescriptionStatus(OrderStatusEnum.UNPAID);

        // Check if the order status is UNPAID
        if (order.getStatus().getStatusId().equals(unpaidStatus.getStatusId())) {
            // Update stock for each item
            for (OrderItem orderItem : order.getOrderItems()) {
                Catalog catalog = orderItem.getCatalog();

                catalog.setStock(catalog.getStock() + orderItem.getQuantity());
                catalogRepository.save(catalog);

            }

            // Mark the order as canceled
            order.setStatus(statusRepository.findByDescriptionStatus(OrderStatusEnum.CANCELED));
            orderRepository.save(order);
            System.out.println("Order successfully marked as canceled.");
        } else {
            // Print an error message if the order is not unpaid
            System.out.println("Order cannot be canceled. Current Status: " + order.getStatus().getDescriptionStatus());
            throw new RuntimeException("Only unpaid orders can be canceled.");
        }


        return ResponseEntity.ok("Order successfully marked as canceled");
    }


    @Transactional
    public List<OrderItemDTO> getOrderItemsByOrderId(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        return order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDTO(
                        orderItem.getCatalog().getProductName(),
                        orderItem.getQuantity(),
                        orderItem.getCatalog().getPrice()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> updateOrderStatusToCompleted(Integer orderId, Integer adminUserId) {


        // Fetch the admin user and validate their permission
        RegisteredCustomer admin = registeredCustomerService.findUserById(adminUserId);

        if (!"admin".equalsIgnoreCase(admin.getPermissions().getPermissionTypeName())) {
            throw new RuntimeException("You do not have permission to update this order.");
        }

        // Fetch the order and print its current status
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        System.out.println("Order Status before update: " + order.getStatus().getDescriptionStatus());

        // Fetch the status entities to ensure correctness
        Status paidStatus = statusRepository.findByDescriptionStatus(OrderStatusEnum.PAID);
        Status completedStatus = statusRepository.findByDescriptionStatus(OrderStatusEnum.COMPLETED);


        // Compare status by IDs instead of descriptions
        if (order.getStatus().getStatusId().equals(paidStatus.getStatusId())) {
            order.setStatus(completedStatus);
            orderRepository.save(order);
            System.out.println("Order Status after update: " + order.getStatus().getDescriptionStatus());
        } else {
            System.out.println("Order Status mismatch or other issue encountered.");
            throw new RuntimeException("Only paid orders can be marked as completed.");
        }


        return ResponseEntity.ok("Order successfully marked as completed");
    }



}
