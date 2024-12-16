package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartItemUpdateFailedException extends BaseException {
    public CartItemUpdateFailedException(ResponseCode responseCode) { super(responseCode); }
}
