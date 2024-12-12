package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.exception.CartItemSaveFailedException;

public interface CartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity) throws CartItemSaveFailedException;
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
