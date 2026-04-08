package com.services.impl;

import com.RequestsDTO.UpdaterCategoryRequest;
import com.dto.Category;
import com.mapper.CategoryMapper;
import com.repository.CategoryRepository;
import com.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Category createCategory(Category category) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, UpdaterCategoryRequest request ) {
        categoryRepository.updateCategoryById(id, request.getName(), request.getShortDescription());
        return categoryMapper.toDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<Category> deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);

        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();

    }
}
