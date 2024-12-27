package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.entity.OrderItem;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.enums.OrderStatus;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.repo.OrderRepo;
import com.shoppingcart.scapi.repo.ProductRepo;
import com.shoppingcart.scapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderedDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) throws OrderNotFoundException {
        Order order = null;
        try {
            order = orderRepo.findById(orderId).get();
            if (order == null) {
                ResponseCode.ORDER_NOT_FOUND.setReason("Invalid ID or Order ID does not exist in the database.");
                throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
            }
            return order;
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.ORDER_NOT_FOUND.setReason(e.getMessage());
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        }
    }
}
