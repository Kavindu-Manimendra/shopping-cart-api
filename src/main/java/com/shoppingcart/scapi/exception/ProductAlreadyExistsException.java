package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ProductAlreadyExistsException extends BaseException {
    public ProductAlreadyExistsException(ResponseCode responseCode) { super(responseCode); }
}
