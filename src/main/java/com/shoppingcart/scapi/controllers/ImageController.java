package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ImageDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.exception.ImageSaveFailedException;
import com.shoppingcart.scapi.exception.ProductNotFoundException;
import com.shoppingcart.scapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponseDto> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        List<ImageDto> imageDtos = null;
        try {
            imageDtos = imageService.saveImages(files, productId);
        } catch (ProductNotFoundException | ImageSaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SAVE_IMAGES_SUCCESS, imageDtos));
    }
}
