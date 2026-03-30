package com.services;

import com.entity.RequestsDTO.UpdaterRequestFood;
import com.entity.dto.Food;
import com.entity.inHead.FilterRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface FoodService {

    Food createFood(Food food);

    Food getById(Long id);

    Food updateFood(Long foodId, UpdaterRequestFood food);

    List<Food> deleteFood(Long foodId);

    Page<Food> getAllFoods(FilterRequest request);

    Food deactivateStatus(Long id, boolean active);

    Food activateStatus(Long id, boolean active;

    Food deactivateFood(Long id, boolean active);

    List<Food> findByCategoryId(Long categoryId);

    List<Food> findByName(String name);

    List<Food> findByPriceBetween(BigDecimal minRange, BigDecimal maxRange);

}
