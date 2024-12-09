package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartSaveFailedException extends BaseException {
    public CartSaveFailedException(ResponseCode responseCode) { super(responseCode); }
}
