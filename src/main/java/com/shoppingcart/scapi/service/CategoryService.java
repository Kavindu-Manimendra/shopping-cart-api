package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.exception.CategoryDeleteFailedException;
import com.shoppingcart.scapi.exception.CategoryNotFoundException;
import com.shoppingcart.scapi.exception.CategoryRetrivedFailedException;
import com.shoppingcart.scapi.exception.CategorySaveFailedException;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id) throws CategoryNotFoundException;
    Category getCategoryByName(String name) throws CategoryNotFoundException;
    List<Category> getAllCategories() throws CategoryRetrivedFailedException;
    Category addCategory(Category category) throws CategorySaveFailedException;
    Category updateCategory(Category category, Long id) throws CategoryNotFoundException, CategorySaveFailedException;
    void deleteCategoryById(Long id) throws CategoryNotFoundException, CategoryDeleteFailedException;
}
