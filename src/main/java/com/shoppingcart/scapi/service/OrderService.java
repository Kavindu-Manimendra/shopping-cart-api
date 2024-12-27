package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.exception.PlaceOrderFailedException;

public interface OrderService {
    Order placeOrder(Long userId) throws PlaceOrderFailedException;
    Order getOrder(Long orderId) throws OrderNotFoundException;
}
