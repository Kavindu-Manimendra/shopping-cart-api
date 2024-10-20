package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class BaseException extends Exception {
    private ResponseCode responseCode;

    public BaseException(ResponseCode responseCode) { this.responseCode = responseCode; }
}
