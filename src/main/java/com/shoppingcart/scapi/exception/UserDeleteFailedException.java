package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class UserDeleteFailedException extends BaseException {
    public UserDeleteFailedException(ResponseCode responseCode) { super(responseCode); }
}
