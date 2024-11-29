package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ConvertToDtoFailedException extends BaseException {
    public ConvertToDtoFailedException(ResponseCode responseCode) { super(responseCode); }
}
