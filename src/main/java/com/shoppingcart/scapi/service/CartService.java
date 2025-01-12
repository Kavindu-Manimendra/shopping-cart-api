package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.CartClearFailedException;
import com.shoppingcart.scapi.exception.CartGetTotalFailedException;
import com.shoppingcart.scapi.exception.CartNotFoundException;
import com.shoppingcart.scapi.exception.CartSaveFailedException;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id) throws CartNotFoundException, CartSaveFailedException;
    void clearCart(Long id) throws CartClearFailedException;
    BigDecimal getTotalPrice(Long id) throws CartGetTotalFailedException;
    Cart initializeNewCart(User user) throws CartSaveFailedException;
    Cart getCartByUserId(Long userId);
}
