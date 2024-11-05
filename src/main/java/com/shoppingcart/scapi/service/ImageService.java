package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.ImageDto;
import com.shoppingcart.scapi.entity.Image;
import com.shoppingcart.scapi.exception.ImageDeleteFailedException;
import com.shoppingcart.scapi.exception.ImageNotFoundException;
import com.shoppingcart.scapi.exception.ImageSaveFailedException;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id) throws ImageNotFoundException;
    void deleteImageById(Long id) throws ImageNotFoundException, ImageDeleteFailedException;
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId) throws ProductNotFoundException, ImageSaveFailedException;
    void updateImage(MultipartFile file, Long imageId) throws ImageNotFoundException, ImageSaveFailedException;
}
