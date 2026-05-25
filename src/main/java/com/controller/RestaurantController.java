package com.controller;

import com.dto.Restaurant;
import com.inHead.FilterRequest;
import com.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name = "Restaurant Controller", description = "Эндпоинты для управления ресторанами")
@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(
            summary = "Получение ресторана по ID",
            description = "Возвращает информацию о ресторане по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан успешно найден"),
            @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(

            @Parameter(description = "ID ресторана")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @Operation(
            summary = "Получение списка ресторанов",
            description = "Возвращает список всех ресторанов с поддержкой пагинации"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список ресторанов успешно получен")
    })
    @GetMapping
    public ResponseEntity<Page<Restaurant>> getAll(

            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Количество элементов на странице")
            @RequestParam(defaultValue = "10") int size
    ) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(
                restaurantService.getAllRestaurants(filterRequest)
        );
    }

    @Operation(
            summary = "Получение ресторана по адресу",
            description = "Возвращает ресторан по указанному адресу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан успешно найден"),
            @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    @GetMapping("/by-address")
    public ResponseEntity<Restaurant> getByAddress(

            @Parameter(description = "Адрес ресторана")
            @RequestParam String address
    ) {
        return ResponseEntity.ok(
                restaurantService.getRestaurantByAddress(address)
        );
    }

    @Operation(
            summary = "Создание ресторана",
            description = "Создает новый ресторан"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные ресторана")
    })
    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(
                restaurantService.createRestaurant(restaurant)
        );
    }

    @Operation(
            summary = "Обновление ресторана",
            description = "Обновляет информацию о ресторане"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(

            @Parameter(description = "ID ресторана")
            @PathVariable Long id,

            @RequestBody Restaurant restaurant
    ) {
        return ResponseEntity.ok(
                restaurantService.updateRestaurant(id, restaurant)
        );
    }

    @Operation(
            summary = "Удаление ресторана",
            description = "Удаляет ресторан по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан успешно удален"),
            @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Restaurant>> delete(

            @Parameter(description = "ID ресторана")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(
                restaurantService.deleteRestaurantById(id)
        );
    }
}
