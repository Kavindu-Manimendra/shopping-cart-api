package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
