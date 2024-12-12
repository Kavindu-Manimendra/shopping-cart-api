package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.CartItem;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.CartItemSaveFailedException;
import com.shoppingcart.scapi.repo.CartItemRepo;
import com.shoppingcart.scapi.repo.CartRepo;
import com.shoppingcart.scapi.service.CartItemService;
import com.shoppingcart.scapi.service.CartService;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void removeItemFromCart(Long cartId, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }
}
