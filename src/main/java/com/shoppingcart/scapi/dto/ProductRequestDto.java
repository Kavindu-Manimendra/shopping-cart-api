package com.shoppingcart.scapi.dto;

import com.shoppingcart.scapi.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
