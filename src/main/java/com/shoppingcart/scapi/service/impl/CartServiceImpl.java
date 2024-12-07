package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.repo.CartRepo;
import com.shoppingcart.scapi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;

    @Override
    public Cart getCart(Long id) {
        return null;
    }

    @Override
    public void clearCart(Long id) {

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return null;
    }
}
