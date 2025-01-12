package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.OrderDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.entity.OrderItem;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.enums.OrderStatus;
import com.shoppingcart.scapi.exception.CartClearFailedException;
import com.shoppingcart.scapi.exception.CartNotFoundException;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.exception.PlaceOrderFailedException;
import com.shoppingcart.scapi.repo.OrderRepo;
import com.shoppingcart.scapi.repo.ProductRepo;
import com.shoppingcart.scapi.service.CartService;
import com.shoppingcart.scapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) throws CartNotFoundException, PlaceOrderFailedException {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null) {
                ResponseCode.CART_NOT_FOUND.setReason("Invalid ID or User ID does not exist in the database.");
                throw new CartNotFoundException(ResponseCode.CART_NOT_FOUND);
            }

            Order order = createOrder(cart);
            List<OrderItem> orderItemList = createOrderItems(order, cart);

            order.setOrderItems(new HashSet<>(orderItemList));
            order.setTotalAmount(calculateTotalAmount(orderItemList));

            Order savedOrder = orderRepo.save(order);
            cartService.clearCart(cart.getId());

            return savedOrder;
        } catch (CartNotFoundException e) {
            throw new CartNotFoundException(ResponseCode.CART_NOT_FOUND);
        } catch (CartClearFailedException e) {
            throw new PlaceOrderFailedException(ResponseCode.CART_CLEAR_FAIL);
        } catch (Exception e) {
            ResponseCode.PLACE_ORDER_FAIL.setReason(e.getMessage());
            throw new PlaceOrderFailedException(ResponseCode.PLACE_ORDER_FAIL);
        }
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
    public OrderDto getOrder(Long orderId) throws OrderNotFoundException {
        OrderDto orderDto = null;
        try {
            Order order = orderRepo.findById(orderId).get();
            if (order == null) {
                ResponseCode.ORDER_NOT_FOUND.setReason("Invalid ID or Order ID does not exist in the database.");
                throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
            }
            orderDto = covertToDto(order);
            return orderDto;
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.ORDER_NOT_FOUND.setReason(e.getMessage());
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        }
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) throws OrderNotFoundException {
        List<OrderDto> orderDtos = null;
        try {
            List<Order> orders = orderRepo.findByUserId(userId);
            if (orders == null) {
                ResponseCode.ORDER_NOT_FOUND.setReason("Invalid ID or User ID does not exist in the database.");
                throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
            }
            for (Order order : orders) {
                orderDtos.add(covertToDto(order));
            }
            return orderDtos;
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.ORDER_NOT_FOUND.setReason(e.getMessage());
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        }
    }

    private OrderDto covertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
