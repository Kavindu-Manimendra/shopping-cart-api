package com.shoppingcart.scapi.dto;

import lombok.Data;

@Data
public class NewTokenRequest {
    private String refreshToken;
}