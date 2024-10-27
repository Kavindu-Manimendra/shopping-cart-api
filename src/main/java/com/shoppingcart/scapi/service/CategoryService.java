package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategoryById(Long id);
}
