package com.shoppingcart.scapi.exception;

import com.shoppingcart.scapi.dto.ResponseCode;

public class ImageSaveFailedException extends BaseException {
    public ImageSaveFailedException(ResponseCode responseCode) { super(responseCode); }
}
