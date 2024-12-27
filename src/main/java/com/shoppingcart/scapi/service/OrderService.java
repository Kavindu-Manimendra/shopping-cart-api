package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.exception.OrderNotFoundException;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId) throws OrderNotFoundException;
}
