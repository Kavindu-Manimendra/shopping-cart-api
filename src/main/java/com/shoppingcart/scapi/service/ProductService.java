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
    List<Product> getProductsByCategory(String category) throws ProductRetrivedFailedException;
    List<Product> getProductsByBrand(String brand) throws ProductRetrivedFailedException;
    List<Product> getProductsByCategoryAndBrand(String category, String brand) throws ProductRetrivedFailedException;
    List<Product> getProductsByName(String name) throws ProductRetrivedFailedException;
    List<Product> getProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException;
    Long countProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException;
}
