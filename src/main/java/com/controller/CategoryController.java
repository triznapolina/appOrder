package com.controller;

import com.RequestsDTO.UpdaterCategoryRequest;
import com.dto.Category;
import com.entity.CategoryEntity;
import com.inHead.FilterRequest;
import com.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Food Category Controller", description = "Эндпоинты для управления категориями блюд")
@RestController
@RequestMapping("/food-category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Создание категории",
            description = "Создает новую категорию блюд"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные категории")
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @Operation(
            summary = "Обновление категории",
            description = "Обновляет информацию о категории по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(

            @Parameter(description = "ID категории")
            @PathVariable Long id,

            @RequestBody UpdaterCategoryRequest request
    ) {
        Category updated = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Получение категории по ID",
            description = "Возвращает категорию блюд по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(

            @Parameter(description = "ID категории")
            @PathVariable Long id
    ) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Получение списка категорий",
            description = "Возвращает список всех категорий с поддержкой пагинации"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список категорий успешно получен")
    })
    @GetMapping("/all")
    public ResponseEntity<Page<CategoryEntity>> getAll(

            @Parameter(description = "Номер страницы")
            @RequestParam int page,

            @Parameter(description = "Количество элементов на странице")
            @RequestParam int size
    ) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(categoryService.getAllCategories(filterRequest));
    }

    @Operation(
            summary = "Удаление категории",
            description = "Удаляет категорию блюд по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Категория успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(

            @Parameter(description = "ID категории")
            @PathVariable Long id
    ) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получение ID категории",
            description = "Возвращает идентификатор категории по объекту категории"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID категории успешно получен"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping("/food-category/all")
    public ResponseEntity<Long> getIdByCategory(@RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.getIdByCategory(category));
    }
}
