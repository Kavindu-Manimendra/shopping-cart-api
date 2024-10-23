package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.AddProductDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import com.shoppingcart.scapi.exception.ProductRetrivedFailedException;
import com.shoppingcart.scapi.exception.ProductSaveFailedException;
import com.shoppingcart.scapi.repo.CategoryRepo;
import com.shoppingcart.scapi.repo.ProductRepo;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // only add variables that are final. ex - ProductRepo
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Product addProduct(AddProductDto request) throws ProductSaveFailedException {
        try {
            Category category = categoryRepo.findByName(request.getCategory().getName());
            if (category == null) {
                category = categoryRepo.save(request.getCategory());
            }

            Product product = new Product(
                    request.getName(),
                    request.getBrand(),
                    request.getPrice(),
                    request.getInventory(),
                    request.getDescription(),
                    category
            );
            return productRepo.save(product);
        } catch (Exception e) {
            ResponseCode.CREATE_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductSaveFailedException(ResponseCode.CREATE_PRODUCT_FAIL);
        }
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        try {
            Product product = productRepo.findById(id).get();
            if (product == null) {
                ResponseCode.PRODUCT_NOT_FOUND.setReason("Invalid ID or Product ID does not exist in the database.");
                throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
            }

            return product;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.PRODUCT_NOT_FOUND.setReason(e.getMessage());
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void deleteProductById(Long id) throws ProductNotFoundException {
        try {
            boolean isExist = productRepo.existsById(id);
            if (!isExist) {
                ResponseCode.PRODUCT_NOT_FOUND.setReason("Invalid ID or Product ID does not exist in the database.");
                throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
            }
            productRepo.deleteById(id);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getAllProducts() throws ProductRetrivedFailedException {
        try {
            return productRepo.findAll();
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws ProductRetrivedFailedException {
        try {
            return productRepo.findByCategoryName(category);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public List<Product> getProductsByBrand(String brand) throws ProductRetrivedFailedException {
        try {
            return productRepo.findByBrand(brand);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) throws ProductRetrivedFailedException {
        try {
            return productRepo.findByCategoryNameAndBrand(category, brand);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public List<Product> getProductsByName(String name) throws ProductRetrivedFailedException {
        try {
            return productRepo.findByName(name);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException {
        try {
            return productRepo.findByBrandAndName(brand, name);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) throws ProductRetrivedFailedException {
        try {
            return productRepo.countByBrandAndName(brand, name);
        } catch (Exception e) {
            ResponseCode.LIST_PRODUCT_FAIL.setReason("Get count by using brand and name failed. " + e.getMessage());
            throw new ProductRetrivedFailedException(ResponseCode.LIST_PRODUCT_FAIL);
        }
    }
}
