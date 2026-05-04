package com.controller;

import com.RequestsDTO.UpdaterCategoryRequest;
import com.dto.Category;
import com.dto.CategoryResponse;
import com.entity.CategoryEntity;
import com.inHead.FilterRequest;
import com.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food-category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody UpdaterCategoryRequest request
    ) {
        Category updated = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryEntity>> getAll(@RequestParam int page, @RequestParam int size) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(categoryService.getAllCategories(filterRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/food-category/all")
    public ResponseEntity<Long> getIdByCategory(@RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.getIdByCategory(category));
    }


}
