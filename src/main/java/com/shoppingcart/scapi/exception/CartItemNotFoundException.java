package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartItemNotFoundException extends BaseException {
    public CartItemNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
