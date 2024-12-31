package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class UserUpdateFailedException extends BaseException {
    public UserUpdateFailedException(ResponseCode responseCode) { super(responseCode); }
}
