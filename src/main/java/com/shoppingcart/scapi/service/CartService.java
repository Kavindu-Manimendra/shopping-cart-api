package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
