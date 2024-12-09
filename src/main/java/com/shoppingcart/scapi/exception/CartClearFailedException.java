package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartClearFailedException extends BaseException {
    public CartClearFailedException(ResponseCode responseCode) { super(responseCode); }
}
