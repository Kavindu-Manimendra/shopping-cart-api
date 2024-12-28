package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
