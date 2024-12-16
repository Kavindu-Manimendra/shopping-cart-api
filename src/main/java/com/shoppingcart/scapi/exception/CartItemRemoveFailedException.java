package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartItemRemoveFailedException extends BaseException {
    public CartItemRemoveFailedException(ResponseCode responseCode) { super(responseCode); }
}
