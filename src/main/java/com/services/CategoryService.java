package com.services;

import com.RequestsDTO.UpdaterCategoryRequest;
import com.dto.Category;
import com.dto.CategoryResponse;
import com.entity.CategoryEntity;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Long id, UpdaterCategoryRequest category);

    Category findCategoryById(Long id);

    Page<CategoryEntity> getAllCategories(FilterRequest filterRequest);

    List<Category> deleteCategoryById(Long id);

}
