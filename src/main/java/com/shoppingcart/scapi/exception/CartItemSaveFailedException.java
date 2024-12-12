package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartItemSaveFailedException extends BaseException {
    public CartItemSaveFailedException(ResponseCode responseCode) { super(responseCode); }
}
