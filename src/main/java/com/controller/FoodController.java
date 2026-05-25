package com.controller;

import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.inHead.FilterRequest;
import com.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Tag(name = "Food Controller", description = "Эндпоинты для управления каталогом блюд")
@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @Operation(
            summary = "Создание блюда",
            description = "Создает новое блюдо с возможностью загрузки изображения. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно создано"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные блюда"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/foods", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Food createFood(

            @Parameter(description = "Данные блюда")
            @RequestPart("food") Food food,

            @Parameter(description = "Изображение блюда")
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return foodService.createFood(food, image);
    }

    @Operation(
            summary = "Обновление блюда",
            description = "Обновляет информацию о блюде и изображение. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/foods/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Food> updateFood(

            @Parameter(description = "ID блюда")
            @PathVariable Long foodId,

            @Parameter(description = "Обновленные данные блюда")
            @RequestPart("food") UpdaterRequestFood food,

            @Parameter(description = "Новое изображение блюда")
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity.ok(foodService.updateFood(foodId, food, image));
    }

    @Operation(
            summary = "Удаление блюда",
            description = "Удаляет блюдо по идентификатору. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<List<Food>> deleteFood(

            @Parameter(description = "ID блюда")
            @PathVariable Long foodId
    ) {
        return ResponseEntity.ok(foodService.deleteFood(foodId));
    }

    @Operation(
            summary = "Получение блюда по ID",
            description = "Возвращает информацию о блюде по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно найдено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено")
    })
    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getById(

            @Parameter(description = "ID блюда")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(foodService.getById(id));
    }

    @Operation(
            summary = "Получение списка блюд",
            description = "Возвращает список всех блюд с поддержкой пагинации"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список блюд успешно получен")
    })
    @GetMapping("/foods/all")
    public ResponseEntity<Page<Food>> getAllFoods(

            @Parameter(description = "Номер страницы")
            @RequestParam int page,

            @Parameter(description = "Количество элементов на странице")
            @RequestParam int size
    ) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(foodService.getAllFoods(filterRequest));
    }

    @Operation(
            summary = "Получение блюд по категории",
            description = "Возвращает список блюд определенной категории"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список блюд успешно получен"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @GetMapping("/foods/category/{categoryId}")
    public ResponseEntity<List<Food>> findByCategoryId(

            @Parameter(description = "ID категории")
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(foodService.findByCategoryId(categoryId));
    }

    @Operation(
            summary = "Поиск блюда по названию",
            description = "Возвращает список блюд по совпадению названия"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результаты поиска успешно получены")
    })
    @GetMapping("/foods/search")
    public ResponseEntity<List<Food>> findByName(

            @Parameter(description = "Название блюда")
            @RequestParam String name
    ) {
        return ResponseEntity.ok(foodService.findByName(name));
    }

    @Operation(
            summary = "Фильтрация блюд по цене",
            description = "Возвращает список блюд в указанном ценовом диапазоне"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список блюд успешно получен")
    })
    @GetMapping("/foods/price")
    public ResponseEntity<List<Food>> findByPriceBetween(

            @Parameter(description = "Минимальная цена")
            @RequestParam BigDecimal minRange,

            @Parameter(description = "Максимальная цена")
            @RequestParam BigDecimal maxRange
    ) {
        return ResponseEntity.ok(foodService.findByPriceBetween(minRange, maxRange));
    }

    @Operation(
            summary = "Фильтрация блюд по категории и цене",
            description = "Возвращает список блюд по категории и ценовому диапазону"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список блюд успешно получен")
    })
    @GetMapping("/filter-by-category/price")
    public ResponseEntity<List<Food>> filterByPriceBetweenAndCategory(

            @Parameter(description = "ID категории")
            @RequestParam Long categoryId,

            @Parameter(description = "Минимальная цена")
            @RequestParam BigDecimal minRange,

            @Parameter(description = "Максимальная цена")
            @RequestParam BigDecimal maxRange
    ) {
        return ResponseEntity.ok(
                foodService.filterByPriceBetweenAndCategory(categoryId, minRange, maxRange)
        );
    }

    @Operation(
            summary = "Получение изображения блюда",
            description = "Возвращает изображение блюда по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно получено"),
            @ApiResponse(responseCode = "404", description = "Изображение или блюдо не найдены")
    })
    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(

            @Parameter(description = "ID блюда")
            @PathVariable Long id
    ) throws IOException {

        Food entity = foodService.getById(id);
        String filename = entity.getImageUrl();

        if (filename.startsWith("http")) {
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        }

        Path path = Paths.get("uploads").resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(Files.probeContentType(path) != null
                        ? MediaType.parseMediaType(Files.probeContentType(path))
                        : MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
