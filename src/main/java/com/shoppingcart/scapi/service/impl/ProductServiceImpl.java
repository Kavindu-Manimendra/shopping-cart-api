package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ImageDto;
import com.shoppingcart.scapi.dto.ProductDto;
import com.shoppingcart.scapi.dto.ProductRequestDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.entity.Image;
import com.shoppingcart.scapi.entity.Product;
import com.shoppingcart.scapi.exception.*;
import com.shoppingcart.scapi.repo.CategoryRepo;
import com.shoppingcart.scapi.repo.ImageRepo;
import com.shoppingcart.scapi.repo.ProductRepo;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // only add variables that are final. ex - ProductRepo
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(ProductRequestDto request) throws ProductAlreadyExistsException, ProductSaveFailedException {
        try {
            if (productExists(request.getName(), request.getBrand())) {
                ResponseCode.PRODUCT_ALREADY_EXISTS.setReason("Product already exists in the database.");
                throw new ProductAlreadyExistsException(ResponseCode.PRODUCT_ALREADY_EXISTS);
            }

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
        } catch (ProductAlreadyExistsException e) {
            throw new ProductAlreadyExistsException(ResponseCode.PRODUCT_ALREADY_EXISTS);
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
    public void deleteProductById(Long id) throws ProductNotFoundException, ProductDeleteFailedException {
        try {
            boolean isExist = productRepo.existsById(id);
            if (!isExist) {
                ResponseCode.PRODUCT_NOT_FOUND.setReason("Invalid ID or Product ID does not exist in the database.");
                throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
            }
            productRepo.deleteById(id);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.DELETE_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductDeleteFailedException(ResponseCode.DELETE_PRODUCT_FAIL);
        }
    }

    @Override
    public Product updateProduct(ProductRequestDto request, Long productId) throws ProductNotFoundException, ProductSaveFailedException {
        try {
            Product existingProduct = productRepo.findById(productId).get();
            if (existingProduct == null) {
                ResponseCode.PRODUCT_NOT_FOUND.setReason("Invalid ID or Product ID does not exist in the database.");
                throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
            }

            existingProduct.setName(request.getName());
            existingProduct.setBrand(request.getBrand());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setInventory(request.getInventory());
            existingProduct.setDescription(request.getDescription());

            Category category = categoryRepo.findByName(request.getCategory().getName());
            if (category == null) {
                category = categoryRepo.save(request.getCategory());
            }
            existingProduct.setCategory(category);

            return productRepo.save(existingProduct);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(ResponseCode.PRODUCT_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.UPDATE_PRODUCT_FAIL.setReason(e.getMessage());
            throw new ProductSaveFailedException(ResponseCode.UPDATE_PRODUCT_FAIL);
        }
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

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) throws ConvertToDtoFailedException {
        List<ProductDto> productDtoList = new ArrayList<>();
        try {
            for (Product p : products) {
                productDtoList.add(convertToDto(p));
            }
            return productDtoList;
        } catch (ConvertToDtoFailedException e) {
            throw new ConvertToDtoFailedException(ResponseCode.CONVERT_TO_DTO_FAIL);
        } catch (Exception e) {
            ResponseCode.CONVERT_TO_PRODUCT_DTO_LIST_FAIL.setReason("Convert to productDto list failed. " + e.getMessage());
            throw new ConvertToDtoFailedException(ResponseCode.CONVERT_TO_PRODUCT_DTO_LIST_FAIL);
        }
    }

    @Override
    public ProductDto convertToDto(Product product) throws ConvertToDtoFailedException {
        try {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            List<Image> images = imageRepo.findByProductId(product.getId());
            List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
            productDto.setImages(imageDtos);
            return productDto;
        } catch (Exception e) {
            ResponseCode.CONVERT_TO_DTO_FAIL.setReason("Product convert to DTO failed. " + e.getMessage());
            throw new ConvertToDtoFailedException(ResponseCode.CONVERT_TO_DTO_FAIL);
        }
    }

    private boolean productExists(String name, String brand) {
        return productRepo.existsByNameAndBrand(name, brand);
    }
}
