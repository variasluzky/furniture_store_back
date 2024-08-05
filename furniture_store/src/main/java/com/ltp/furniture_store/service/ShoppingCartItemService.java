package com.ltp.furniture_store.service;

import com.ltp.furniture_store.entity.ShoppingCartItem;
import com.ltp.furniture_store.entity.ShoppingCartItemId;
import com.ltp.furniture_store.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

////use this service to save shoppingCartItem
@Service
public class ShoppingCartItemService {

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    public void saveShoppingCartItem(ShoppingCartItem shoppingCartItem){
        if(shoppingCartItem.getId() == null){
            ShoppingCartItemId id = new ShoppingCartItemId();
            id.setCartId(shoppingCartItem.getShoppingCart().getCartId());
            id.setCartItemId(generateNextShoppingCartItemId(shoppingCartItem.getShoppingCart().getCartId()));
            shoppingCartItem.setId(id);
        }
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    private Integer generateNextShoppingCartItemId(Integer cartId){
      Integer maxShoppingCartItemId = shoppingCartItemRepository.findMaxShoppingCartIdByCartId(cartId);
          return (maxShoppingCartItemId != null) ? maxShoppingCartItemId +1: 1;

   }
}

