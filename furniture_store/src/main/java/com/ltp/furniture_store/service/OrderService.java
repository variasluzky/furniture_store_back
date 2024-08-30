package com.ltp.furniture_store.service;

import com.ltp.furniture_store.entity.*;
import com.ltp.furniture_store.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Order updateOrderAddress(Integer orderId, String newAddress, Integer userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (!order.getCustomer().getCustomerId().equals(userId)) {
            throw new RuntimeException("You do not have permission to update this order.");
        }

        if (order.getDelivery() && order.getStatus().equals(OrderStatusEnum.UNPAID)) {
            order.setAddress(newAddress);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Address update is not allowed.");
        }

        return order;
    }
    @Transactional
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        RegisteredCustomer customer = registeredCustomerService.findUserById(userId);
        List<Order> orders = orderRepository.findByCustomer(customer);

        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getAddress(),
                        order.getTotalPrice(),
                        order.getStatus().getDescriptionStatus().toString(),
                        order.getOrderItems().stream()
                                .map(orderItem -> new OrderItemDTO(
                                        orderItem.getCatalog().getProductName(),
                                        orderItem.getQuantity(),
                                        orderItem.getCatalog().getPrice()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getStatus().equals(OrderStatusEnum.UNPAID)) {
            // Update stock for each item
            for (OrderItem orderItem : order.getOrderItems()) {
                Catalog catalog = orderItem.getCatalog();
                catalog.setStock(catalog.getStock() + orderItem.getQuantity());
                catalogRepository.save(catalog);
            }

            // Delete the order
            orderRepository.delete(order);
        } else {
            throw new RuntimeException("Only unpaid orders can be canceled.");
        }
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

}
