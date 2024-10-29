package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CategoryDeleteFailedException extends BaseException {
    public CategoryDeleteFailedException(ResponseCode responseCode) { super(responseCode); }
}
