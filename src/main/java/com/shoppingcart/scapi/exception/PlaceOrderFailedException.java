package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class PlaceOrderFailedException extends BaseException {
    public PlaceOrderFailedException(ResponseCode responseCode) { super(responseCode); }
}
