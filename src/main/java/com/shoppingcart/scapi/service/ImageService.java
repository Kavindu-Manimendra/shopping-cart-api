package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Image;
import com.shoppingcart.scapi.exception.ImageDeleteFailedException;
import com.shoppingcart.scapi.exception.ImageNotFoundException;
import com.shoppingcart.scapi.exception.ImageSaveFailedException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image getImageById(Long id) throws ImageNotFoundException;
    void deleteImageById(Long id) throws ImageNotFoundException, ImageDeleteFailedException;
    Image saveImage(MultipartFile file, Long productId);
    void updateImage(MultipartFile file, Long imageId) throws ImageNotFoundException, ImageSaveFailedException;
}
