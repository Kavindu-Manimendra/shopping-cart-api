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
    DELETE_PRODUCT_FAIL("Product deletion failed!", ""),
    CONVERT_TO_PRODUCT_DTO_LIST_FAIL("Convert to productDto list failed!", ""),

    CATEGORY_NOT_FOUND("Category not found!", ""),
    LIST_CATEGORY_FAIL("Category listing failed!", ""),
    DELETE_CATEGORY_FAIL("Category deletion failed!", ""),
    UPDATE_CATEGORY_FAIL("Category update failed!", ""),
    CREATE_CATEGORY_FAIL("Category creation failed!", ""),

    IMAGE_NOT_FOUND("Image not found!", ""),
    DELETE_IMAGE_FAIL("Image deletion failed!", ""),
    UPDATE_IMAGE_FAIL("Image update failed!", ""),
    CREATE_IMAGE_FAIL("Image creation failed!", ""),
    SAVE_IMAGES_SUCCESS("Images saved successfully!", ""),
    UPDATE_IMAGE_SUCCESS("Image updated successfully!", ""),
    DELETE_IMAGE_SUCCESS("Image deletion successfully!", ""),

    CONVERT_TO_DTO_FAIL("Convert to DTO failed!", ""),

    CART_NOT_FOUND("Cart not found!", ""),
    CART_SAVE_FAIL("Cart save failed!", "");

    private String message;
    private String reason;

    public void setMessage(String message) { this.message = message; }

    public void setReason(String reason) { this.reason = reason; }
}
