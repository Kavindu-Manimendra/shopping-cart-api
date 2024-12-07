package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.exception.CartNotFoundException;
import com.shoppingcart.scapi.exception.CartSaveFailedException;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id) throws CartNotFoundException, CartSaveFailedException;
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
