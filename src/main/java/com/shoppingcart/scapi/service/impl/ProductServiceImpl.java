package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import com.shoppingcart.scapi.repo.ProductRepo;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        try {
            Optional<Product> product = productRepo.findById(id);
            if (product == null) {
                ResponseCode.PRODUCT_NOT_FOUND.setReason("Invalid ID or Product ID does not exist in the database.");
                throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
            }

            return product.get();
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.PRODUCT_NOT_FOUND.setReason(e.getMessage());
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return List.of();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return 0;
    }
}
