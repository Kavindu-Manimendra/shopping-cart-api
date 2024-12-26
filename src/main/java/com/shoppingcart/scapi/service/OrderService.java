package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Order;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
