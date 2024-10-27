package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CategoryRetrivedFailedException extends BaseException{
    public CategoryRetrivedFailedException(ResponseCode responseCode) { super(responseCode); }
}
