package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ImageNotFoundException extends BaseException {
    public ImageNotFoundException(ResponseCode responseCode) { super(responseCode); }
}
