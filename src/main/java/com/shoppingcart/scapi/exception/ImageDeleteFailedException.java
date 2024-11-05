package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ImageDeleteFailedException extends BaseException {
    public ImageDeleteFailedException(ResponseCode responseCode) { super(responseCode); }
}
