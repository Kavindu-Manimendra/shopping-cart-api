package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.repo.OrderRepo;
import com.shoppingcart.scapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
        return null;
    }
}
