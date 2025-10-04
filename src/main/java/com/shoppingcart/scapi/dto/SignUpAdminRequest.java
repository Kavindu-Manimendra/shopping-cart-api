package com.shoppingcart.scapi.dto;

import lombok.Data;

@Data
public class SignUpAdminRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
