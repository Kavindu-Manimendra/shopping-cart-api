package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
