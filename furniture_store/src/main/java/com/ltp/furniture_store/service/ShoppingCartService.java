package com.ltp.furniture_store.service;

import com.ltp.furniture_store.entity.*;
import com.ltp.furniture_store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ShoppingCartStatusRepository shoppingCartStatusRepository;

    @Autowired
    private RegisteredCustomerRepository registeredCustomerRepository;

    // Create or update a shopping cart for a registered customer
    public ShoppingCart createOrUpdateCart(RegisteredCustomer customer) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findByCustomerAndShoppingCartStatus_StatusDescription(customer, ShoppingCartStatusEnum.ACTIVE);

        ShoppingCart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            ShoppingCartStatus activeStatus = shoppingCartStatusRepository.findByStatusDescription(ShoppingCartStatusEnum.ACTIVE);
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart.setShoppingCartStatus(activeStatus);
            cart.setCreatedAt(new Date());
            cart.setUpdatedAt(new Date());
            shoppingCartRepository.save(cart);
        }
        return cart;
    }

    // Complete the cart
    @Transactional
    public void completeCart(ShoppingCart cart) {
        ShoppingCartStatus completedStatus = shoppingCartStatusRepository.findByStatusDescription(ShoppingCartStatusEnum.COMPLETED);
        cart.setShoppingCartStatus(completedStatus);
        cart.setUpdatedAt(new Date());
        shoppingCartRepository.save(cart);
    }

    public List<ShoppingCartItemDTO> getCartItemsByCustomerId(Integer customerId) {
        // Fetch the RegisteredCustomer using the customerId
        RegisteredCustomer customer = registeredCustomerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + customerId));

        Optional<ShoppingCart> cart = shoppingCartRepository.findByCustomerAndShoppingCartStatus_StatusDescription(
                customer, ShoppingCartStatusEnum.ACTIVE);

        if (cart.isPresent()) {
            List<ShoppingCartItem> items = shoppingCartItemRepository.findByShoppingCart(cart.get());
            return items.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public void updateCartItemQuantity(ShoppingCart cart, Catalog catalog, int quantity) {
        ShoppingCartItemId itemId = new ShoppingCartItemId(cart.getCartId(), Math.toIntExact(catalog.getProductID()));
        Optional<ShoppingCartItem> existingItem = shoppingCartItemRepository.findById(itemId);

        if (quantity > 0) {
            // Add or update item in the cart
            if (existingItem.isPresent()) {
                ShoppingCartItem item = existingItem.get();
                int difference = quantity - item.getQuantityCart();
                if (catalog.getStock() < difference) {
                    throw new IllegalArgumentException("Insufficient stock for product: " + catalog.getProductName());
                }
                item.setQuantityCart(quantity);
                catalog.setStock(catalog.getStock() - difference);
                shoppingCartItemRepository.save(item);
            } else {
                // Add new item if not already in the cart
                if (catalog.getStock() < quantity) {
                    throw new IllegalArgumentException("Insufficient stock for product: " + catalog.getProductName());
                }
                ShoppingCartItem newItem = new ShoppingCartItem(itemId, cart, catalog, quantity);
                catalog.setStock(catalog.getStock() - quantity);
                shoppingCartItemRepository.save(newItem);
            }
        } else {
            // Remove item if quantity is zero
            if (existingItem.isPresent()) {
                ShoppingCartItem item = existingItem.get();
                catalog.setStock(catalog.getStock() + item.getQuantityCart());
                shoppingCartItemRepository.delete(item);
            }
        }

        // Update the cart
        if (shoppingCartItemRepository.findByShoppingCart(cart).isEmpty()) {
            ShoppingCartStatus canceledStatus = shoppingCartStatusRepository.findByStatusDescription(ShoppingCartStatusEnum.CANCELED);
            cart.setShoppingCartStatus(canceledStatus);
        }

        shoppingCartRepository.save(cart);
        catalogRepository.save(catalog);
    }

    @Transactional
    public ResponseEntity<String> clearCart(Integer cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findById(cartId);

        if (cartOptional.isEmpty()) {
            return new ResponseEntity<>("Shopping cart not found with id: " + cartId, HttpStatus.NOT_FOUND);
        }

        ShoppingCart cart = cartOptional.get();

        // Proceed with clearing the cart
        List<ShoppingCartItem> items = shoppingCartItemRepository.findByShoppingCart(cart);

        for (ShoppingCartItem item : items) {
            Catalog catalogItem = item.getCatalog();
            catalogItem.setStock(catalogItem.getStock() + item.getQuantityCart());
            catalogRepository.save(catalogItem);

            shoppingCartItemRepository.delete(item);
        }

        // Update the cart's status to reflect that it has been cleared
        ShoppingCartStatus clearedStatus = shoppingCartStatusRepository.findByStatusDescription(ShoppingCartStatusEnum.CANCELED);
        cart.setShoppingCartStatus(clearedStatus);
        cart.setUpdatedAt(new Date());

        // Save the updated cart
        shoppingCartRepository.save(cart);

        return new ResponseEntity<>("Cart cleared successfully", HttpStatus.OK);
    }

    public List<ShoppingCart> getActiveShoppingCarts() {
        return shoppingCartRepository.findByShoppingCartStatus_StatusDescription(ShoppingCartStatusEnum.ACTIVE);
    }

    public ShoppingCartDTO convertToDTO(ShoppingCart cart) {

        String statusDescription = cart.getShoppingCartStatus().getStatusDescription().toString();

        return new ShoppingCartDTO(
                cart.getCartId(),
                cart.getCustomer().getCustomerId(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                statusDescription
        );
    }
    public ShoppingCartItemDTO convertToDTO(ShoppingCartItem item) {
        ShoppingCartItemDTO dto = new ShoppingCartItemDTO();
        dto.setProductId(item.getCatalog().getProductID());
        dto.setProductName(item.getCatalog().getProductName());
        dto.setQuantity(item.getQuantityCart());
        dto.setPrice(item.getCatalog().getPrice());
        dto.setPromotion(item.getCatalog().getPromotion().getDiscount());
        dto.setTotalPrice(item.getCatalog().getPrice().multiply(new BigDecimal(item.getQuantityCart())));
        return dto;
    }

    @Transactional
    public ShoppingCartItem addItemToCart(ShoppingCart cart, Catalog catalog, int quantity) {
        Optional<ShoppingCartItem> existingItem = shoppingCartItemRepository.findById(
                new ShoppingCartItemId(cart.getCartId(), Math.toIntExact(catalog.getProductID())));

        int totalQuantity = quantity;
        if (existingItem.isPresent()) {
            totalQuantity += existingItem.get().getQuantityCart();
        }

        if (catalog.getStock() < totalQuantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + catalog.getProductName());
        }

        catalog.setStock(catalog.getStock() - quantity);
        catalogRepository.save(catalog);

        ShoppingCartItem item;
        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setQuantityCart(totalQuantity);
        } else {
            ShoppingCartItemId itemId = new ShoppingCartItemId(cart.getCartId(), Math.toIntExact(catalog.getProductID()));
            item = new ShoppingCartItem(itemId, cart, catalog, totalQuantity);
        }

        shoppingCartItemRepository.save(item);
        cart.setUpdatedAt(new Date());
        shoppingCartRepository.save(cart);

        return item;
    }
}
