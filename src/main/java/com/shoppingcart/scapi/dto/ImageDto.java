package com.shoppingcart.scapi.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
