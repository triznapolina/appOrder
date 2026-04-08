package com.services;

import com.RequestsDTO.UpdaterCategoryRequest;
import com.dto.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Long id, UpdaterCategoryRequest category);

    Category findCategoryById(Long id);

    List<Category> getAllCategories();

    List<Category> deleteCategoryById(Long id);

}
