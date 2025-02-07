package com.shoppingcart.scapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank // can not pass empty values
    private String email;
    @NotBlank
    private String password;
}
