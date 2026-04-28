package com.controller;

import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.inHead.FilterRequest;
import com.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class FoodController {

   private final FoodService foodService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/foods")
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        return ResponseEntity.ok(foodService.createFood(food));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/foods/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable Long foodId, @RequestBody UpdaterRequestFood food) {
        return ResponseEntity.ok(foodService.updateFood(foodId, food));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<List<Food>> deleteFood(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.deleteFood(foodId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/foods/{id}/deactivate")
    public ResponseEntity<Food> deactivateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(foodService.deactivateStatus(id, active));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/foods/{id}/activate")
    public ResponseEntity<Food> activateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(foodService.activateStatus(id, active));
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getById(id));
    }

    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/foods/category/{categoryId}")
    public ResponseEntity<List<Food>> findByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(foodService.findByCategoryId(categoryId));
    }

    @GetMapping("/foods/search")
    public ResponseEntity<List<Food>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(foodService.findByName(name));
    }

    @GetMapping("/foods/price")
    public ResponseEntity<List<Food>> findByPriceBetween(@RequestParam BigDecimal minRange, @RequestParam BigDecimal maxRange) {
        return ResponseEntity.ok(foodService.findByPriceBetween(minRange, maxRange));
    }

}
