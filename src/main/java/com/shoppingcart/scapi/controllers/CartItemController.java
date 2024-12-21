package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.exception.CartItemNotFoundException;
import com.shoppingcart.scapi.exception.CartItemRemoveFailedException;
import com.shoppingcart.scapi.exception.CartItemSaveFailedException;
import com.shoppingcart.scapi.exception.CartItemUpdateFailedException;
import com.shoppingcart.scapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/item/add")
    public ResponseEntity<APIResponseDto> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId,
                                                        @RequestParam Integer quantity) {
        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
        } catch (CartItemSaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Cart item added to cart successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<APIResponseDto> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
        } catch (CartItemNotFoundException | CartItemRemoveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Cart item removed from cart successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<APIResponseDto> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId,
                                                             @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
        } catch (CartItemUpdateFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Cart item updated from cart successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }

}
