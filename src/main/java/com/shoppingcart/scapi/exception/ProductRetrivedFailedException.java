package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ProductRetrivedFailedException extends BaseException {
    public ProductRetrivedFailedException(ResponseCode responseCode) { super(responseCode); }
}
