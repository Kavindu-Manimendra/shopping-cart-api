package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Image;
import com.shoppingcart.scapi.exception.ImageDeleteFailedException;
import com.shoppingcart.scapi.exception.ImageNotFoundException;
import com.shoppingcart.scapi.exception.ImageSaveFailedException;
import com.shoppingcart.scapi.repo.ImageRepo;
import com.shoppingcart.scapi.service.ImageService;
import com.shoppingcart.scapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) throws ImageNotFoundException {
        try {
            Image image = imageRepo.findById(id).get();
            if (image == null) {
                ResponseCode.IMAGE_NOT_FOUND.setReason("Invalid ID or Image ID does not exist in the database.");
                throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
            }
            return image;
        } catch (ImageNotFoundException e) {
            throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.IMAGE_NOT_FOUND.setReason(e.getMessage());
            throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
        }
    }

    @Override
    public void deleteImageById(Long id) throws ImageNotFoundException, ImageDeleteFailedException {
        try {
            boolean isExist = imageRepo.existsById(id);
            if (!isExist) {
                ResponseCode.IMAGE_NOT_FOUND.setReason("Invalid ID or Image ID does not exist in the database.");
                throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
            }
            imageRepo.deleteById(id);
        } catch (ImageNotFoundException e) {
            throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.DELETE_IMAGE_FAIL.setReason(e.getMessage());
            throw new ImageDeleteFailedException(ResponseCode.DELETE_IMAGE_FAIL);
        }
    }

    @Override
    public Image saveImage(MultipartFile file, Long productId) {
        return null;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) throws ImageNotFoundException, ImageSaveFailedException {
        try {
            Image image = getImageById(imageId);
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (ImageNotFoundException e) {
            throw new ImageNotFoundException(ResponseCode.IMAGE_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.UPDATE_IMAGE_FAIL.setReason(e.getMessage());
            throw new ImageSaveFailedException(ResponseCode.UPDATE_IMAGE_FAIL);
        }
    }
}
