package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import com.shoppingcart.scapi.exception.ProductRetrivedFailedException;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponseDto> getAllProducts() {
        List<Product> products = null;
        try {
            products = productService.getAllProducts();
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, products));
    }

    // @PathVariable("productId") Long id, productId will be equal to id
    @GetMapping("/{productId}")
    public ResponseEntity<APIResponseDto> getProductById(@PathVariable("productId") Long id) {
        Product product = null;
        try {
            product = productService.getProductById(id);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product getting successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, product));
    }
}
