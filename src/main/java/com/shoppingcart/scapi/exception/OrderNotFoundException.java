package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
