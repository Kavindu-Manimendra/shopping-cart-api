package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ProductDeleteFailedException extends BaseException {
    public ProductDeleteFailedException(ResponseCode responseCode) { super(responseCode); }
}
