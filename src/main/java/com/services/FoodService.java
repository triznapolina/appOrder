package com.services;

import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface FoodService {

    Food createFood(Food food, MultipartFile file);

    Food getById(Long id);

    Food updateFood(Long foodId, UpdaterRequestFood food, MultipartFile image);

    List<Food> deleteFood(Long foodId);

    Page<Food> getAllFoods(FilterRequest filterRequest);

    List<Food> findByCategoryId(Long categoryId);

    List<Food> findByName(String name);

    List<Food> findByPriceBetween(BigDecimal minRange, BigDecimal maxRange);

    List<Food> filterByPriceBetweenAndCategory(Long categoryId, BigDecimal minRange, BigDecimal maxRange);
}
