package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class UserCreateFailedException extends BaseException {
    public UserCreateFailedException(ResponseCode responseCode) { super(responseCode); }
}
