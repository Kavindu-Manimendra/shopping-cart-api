package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.CartItem;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.*;
import com.shoppingcart.scapi.repo.CartItemRepo;
import com.shoppingcart.scapi.repo.CartRepo;
import com.shoppingcart.scapi.service.CartItemService;
import com.shoppingcart.scapi.service.CartService;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepo cartItemRepo;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepo cartRepo;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) throws CartItemSaveFailedException {
        try {
            Cart cart = cartService.getCart(cartId);
            Product product = productService.getProductById(productId);
            CartItem cartItem = cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElse(new CartItem());

            if (cartItem.getId() == null) {
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(product.getPrice());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }

            cartItem.setTotalPrice();
            cart.addItem(cartItem);
            cartItemRepo.save(cartItem);
            cartRepo.save(cart);
        } catch (Exception e) {
            ResponseCode.ADD_ITEM_TO_CART_FAIL.setReason(e.getMessage());
            throw new CartItemSaveFailedException(ResponseCode.ADD_ITEM_TO_CART_FAIL);
        }
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) throws CartItemNotFoundException, CartItemRemoveFailedException {
        try {
            Cart cart = cartService.getCart(cartId);
            CartItem itemToRemove = cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElseThrow(() -> new CartItemNotFoundException(ResponseCode.CART_ITEM_NOT_FOUND));
            cart.removeItem(itemToRemove);
            cartRepo.save(cart);
        } catch (CartItemNotFoundException e) {
            throw new CartItemNotFoundException(ResponseCode.CART_ITEM_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.REMOVE_CART_ITEM_FAIL.setReason(e.getMessage());
            throw new CartItemRemoveFailedException(ResponseCode.REMOVE_CART_ITEM_FAIL);
        }
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws CartItemUpdateFailedException {
        try {
            Cart cart = cartService.getCart(cartId);
            cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .ifPresent(item -> {
                        item.setQuantity(quantity);
                        item.setUnitPrice(item.getProduct().getPrice());
                        item.setTotalPrice();
                    });
            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);
            cartRepo.save(cart);
        } catch (Exception e) {
            ResponseCode.UPDATE_ITEM_QUANTITY_FAIL.setReason(e.getMessage());
            throw new CartItemUpdateFailedException(ResponseCode.UPDATE_ITEM_QUANTITY_FAIL);
        }
    }

    public CartItem getCartItem(Long cartId, Long productId) throws CartItemNotFoundException, CartNotFoundException {
        Cart cart = null;
        try {
            cart = cartService.getCart(cartId);
            return cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElseThrow(() -> new CartItemNotFoundException(ResponseCode.CART_ITEM_NOT_FOUND));
        } catch (CartNotFoundException | CartSaveFailedException e) {
            ResponseCode.CART_NOT_FOUND.setReason(e.getMessage());
            throw new CartNotFoundException(ResponseCode.CART_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.CART_ITEM_NOT_FOUND.setReason(e.getMessage());
            throw new CartItemNotFoundException(ResponseCode.CART_ITEM_NOT_FOUND);
        }
    }
}
