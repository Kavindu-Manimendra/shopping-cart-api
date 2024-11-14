package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.exception.CategoryDeleteFailedException;
import com.shoppingcart.scapi.exception.CategoryNotFoundException;
import com.shoppingcart.scapi.exception.CategoryRetrivedFailedException;
import com.shoppingcart.scapi.exception.CategorySaveFailedException;
import com.shoppingcart.scapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<APIResponseDto> getAllCategories() {
        List<Category> categories = null;
        try {
            categories = categoryService.getAllCategories();
        } catch (CategoryRetrivedFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category listing successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, categories));
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponseDto> addCategory(@RequestBody Category category) {
        Category savedCategory = null;
        try {
            savedCategory = categoryService.addCategory(category);
        } catch (CategorySaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category creation successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, savedCategory));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<APIResponseDto> getCategoryById(@PathVariable Long id) {
        Category category = null;
        try {
            category = categoryService.getCategoryById(id);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category getting successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, category));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<APIResponseDto> getCategoryByName(@PathVariable String name) {
        Category category = null;
        try {
            category = categoryService.getCategoryByName(name);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category getting successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponseDto> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
        } catch (CategoryNotFoundException | CategoryDeleteFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category deletion successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponseDto> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category savedCategory = null;
        try {
            savedCategory = categoryService.updateCategory(category, id);
        } catch (CategoryNotFoundException | CategorySaveFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Category update successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, savedCategory));
    }
}
