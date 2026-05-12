package com.controller;

import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.entity.FoodEntity;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class FoodController {

   private final FoodService foodService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/foods", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Food createFood(
            @RequestPart("food") Food food,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return foodService.createFood(food, image);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/foods/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Food> updateFood(
            @PathVariable Long foodId,
            @RequestPart("food") UpdaterRequestFood food,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ResponseEntity.ok(foodService.updateFood(foodId, food, image));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<List<Food>> deleteFood(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.deleteFood(foodId));
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getById(id));
    }

    @GetMapping("/foods/all")
    public ResponseEntity<Page<Food>> getAllFoods(@RequestParam int page, @RequestParam int size) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        return ResponseEntity.ok(foodService.getAllFoods(filterRequest));
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


    @GetMapping("/filter-by-category/price")
    public ResponseEntity<List<Food>> filterByPriceBetweenAndCategory(@RequestParam Long categoryId,
                                                                      @RequestParam BigDecimal minRange,
                                                                      @RequestParam BigDecimal maxRange) {
        return ResponseEntity.ok(foodService.filterByPriceBetweenAndCategory(categoryId, minRange, maxRange));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {

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
