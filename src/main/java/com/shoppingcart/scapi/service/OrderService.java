package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.OrderDto;
import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.exception.CartNotFoundException;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.exception.PlaceOrderFailedException;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId) throws CartNotFoundException, PlaceOrderFailedException;
    OrderDto getOrder(Long orderId) throws OrderNotFoundException;
    List<OrderDto> getUserOrders(Long userId) throws OrderNotFoundException;
    OrderDto covertToDto(Order order);
}
