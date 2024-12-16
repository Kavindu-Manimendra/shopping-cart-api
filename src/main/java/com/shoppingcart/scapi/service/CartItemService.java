package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.CartItem;
import com.shoppingcart.scapi.exception.*;

public interface CartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity) throws CartItemSaveFailedException;
    void removeItemFromCart(Long cartId, Long productId) throws CartItemNotFoundException, CartItemRemoveFailedException;
    void updateItemQuantity(Long cartId, Long productId, int quantity) throws CartItemUpdateFailedException;
    CartItem getCartItem(Long cartId, Long productId) throws CartItemNotFoundException, CartNotFoundException;
}
