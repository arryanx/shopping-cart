package com.aryanproject.dreamshop.service.category;

import com.aryanproject.dreamshop.model.Category;

import java.util.List;

public interface CategoryServiceInterface {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category updateCategory(Category category, Long id);
    Category addcategory(Category category);
    void deleteCategoryById(Long id);
}
