package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import com.shoppingcart.scapi.exception.ProductRetrivedFailedException;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product getProductById(Long id) throws ProductNotFoundException;
    void deleteProductById(Long id) throws ProductNotFoundException;
    void updateProduct(Product product, Long productId);
    List<Product> getAllProducts() throws ProductRetrivedFailedException;
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
