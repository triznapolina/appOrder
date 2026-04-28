package com.controller;

import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.dto.Restaurant;
import com.inHead.FilterRequest;
import com.services.FoodService;
import com.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 📍 Получить по ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable long id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    // 📍 Получить все с пагинацией
    @GetMapping
    public ResponseEntity<Page<Restaurant>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(restaurantService.getAllRestaurants(filterRequest));
    }

    // 📍 Поиск по адресу
    @GetMapping("/by-address")
    public ResponseEntity<Restaurant> getByAddress(@RequestParam String address) {
        return ResponseEntity.ok(restaurantService.getRestaurantByAddress(address));
    }

    // 📍 Создать
    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
    }

    // 📍 Обновить
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(
            @PathVariable Long id,
            @RequestBody Restaurant restaurant
    ) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    // 📍 Удалить
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Restaurant>> delete(@PathVariable long id) {
        return ResponseEntity.ok(restaurantService.deleteRestaurantById(id));
    }
}
