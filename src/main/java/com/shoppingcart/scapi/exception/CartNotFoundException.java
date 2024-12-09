package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartNotFoundException extends BaseException {
    public CartNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
