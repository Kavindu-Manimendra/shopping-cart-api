package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CartGetTotalFailedException extends BaseException {
    public CartGetTotalFailedException(ResponseCode responseCode) { super(responseCode); }
}
