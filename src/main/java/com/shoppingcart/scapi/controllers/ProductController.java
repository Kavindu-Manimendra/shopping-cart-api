package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ProductDto;
import com.shoppingcart.scapi.dto.ProductRequestDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.*;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponseDto> getAllProducts() {
        List<Product> products = null;
        List<ProductDto> productDtoList = null;
        try {
            products = productService.getAllProducts();
            productDtoList = productService.getConvertedProducts(products);
        } catch (ProductRetrivedFailedException | ConvertToDtoFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, productDtoList));
    }

    // @PathVariable("productId") Long id, productId will be equal to id
    @GetMapping("/{productId}")
    public ResponseEntity<APIResponseDto> getProductById(@PathVariable("productId") Long id) {
        Product product = null;
        ProductDto productDto = null;
        try {
            product = productService.getProductById(id);
            productDto = productService.convertToDto(product);
        } catch (ProductNotFoundException | ConvertToDtoFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product getting successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, productDto));
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponseDto> addProduct(@RequestBody ProductRequestDto product) {
        Product savedProduct = null;
        try {
            savedProduct = productService.addProduct(product);
        } catch (ProductSaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product added successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, savedProduct));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<APIResponseDto> updateProduct(@RequestBody ProductRequestDto product, @PathVariable("productId") Long id) {
        Product updatedProduct = null;
        try {
            updatedProduct = productService.updateProduct(product, id);
        } catch (ProductNotFoundException | ProductSaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product updated successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, updatedProduct));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<APIResponseDto> deleteProduct(@PathVariable("productId") Long id) {
        try {
            productService.deleteProductById(id);
        } catch (ProductNotFoundException | ProductDeleteFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product deleted successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }

    @GetMapping("/get-by-brand-name")
    public ResponseEntity<APIResponseDto> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        List<Product> products = null;
        List<ProductDto> productDtos = null;
        try {
            products = productService.getProductsByBrandAndName(brandName, productName);
            productDtos = productService.getConvertedProducts(products);
        } catch (ProductRetrivedFailedException | ConvertToDtoFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, productDtos));
    }

    @GetMapping("/get-by-category-brand")
    public ResponseEntity<APIResponseDto> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        List<Product> products = null;
        try {
            products = productService.getProductsByCategoryAndBrand(category, brand);
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, products));
    }

    @GetMapping("/get/{productName}")
    public ResponseEntity<APIResponseDto> getProductsByName(@PathVariable String productName) {
        List<Product> products = null;
        try {
            products = productService.getProductsByName(productName);
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, products));
    }

    @GetMapping("/get/by-brand")
    public ResponseEntity<APIResponseDto> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = null;
        try {
            products = productService.getProductsByBrand(brand);
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, products));
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<APIResponseDto> getProductsByCategory(@PathVariable String category) {
        List<Product> products = null;
        try {
            products = productService.getProductsByCategory(category);
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Product listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, products));
    }

    @GetMapping("get-count-brand-name")
    public ResponseEntity<APIResponseDto> getCountOfProductsByBrandName(@RequestParam String brand, @RequestParam String name) {
        Long count = null;
        try {
            count = productService.countProductsByBrandAndName(brand, name);
        } catch (ProductRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Get Product count success!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, count));
    }
}
