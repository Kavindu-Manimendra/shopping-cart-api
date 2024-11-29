package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.ProductDto;
import com.shoppingcart.scapi.dto.ProductRequestDto;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.*;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductRequestDto request) throws ProductSaveFailedException;
    Product getProductById(Long id) throws ProductNotFoundException;
    void deleteProductById(Long id) throws ProductNotFoundException, ProductDeleteFailedException;
    Product updateProduct(ProductRequestDto request, Long productId) throws ProductNotFoundException, ProductSaveFailedException;
    List<Product> getAllProducts() throws ProductRetrivedFailedException;
    List<Product> getProductsByCategory(String category) throws ProductRetrivedFailedException;
    List<Product> getProductsByBrand(String brand) throws ProductRetrivedFailedException;
    List<Product> getProductsByCategoryAndBrand(String category, String brand) throws ProductRetrivedFailedException;
    List<Product> getProductsByName(String name) throws ProductRetrivedFailedException;
    List<Product> getProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException;
    Long countProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException;
    ProductDto convertToDto(Product product) throws ConvertToDtoFailedException;
}
