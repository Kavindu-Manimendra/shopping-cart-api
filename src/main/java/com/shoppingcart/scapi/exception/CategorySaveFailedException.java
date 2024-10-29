package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class CategorySaveFailedException extends BaseException {
    public CategorySaveFailedException(ResponseCode responseCode) { super(responseCode); }
}
