package com.shoppingcart.scapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("Success", ""),
    ERROR("Unknown Error", ""),

    PRODUCT_NOT_FOUND("Product not found!", ""),
    LIST_PRODUCT_FAIL("Product listing failed!", ""),
    CREATE_PRODUCT_FAIL("Product creation failed!", ""),
    UPDATE_PRODUCT_FAIL("Product update failed!", ""),

    CATEGORY_NOT_FOUND("Category not found!", ""),
    LIST_CATEGORY_FAIL("Category listing failed!", ""),
    DELETE_CATEGORY_FAIL("Category deletion failed!", "");

    private String message;
    private String reason;

    public void setMessage(String message) { this.message = message; }

    public void setReason(String reason) { this.reason = reason; }
}
