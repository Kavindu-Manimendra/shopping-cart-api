package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ProductSaveFailedException extends BaseException {
    public ProductSaveFailedException(ResponseCode responseCode) { super(responseCode); }
}
