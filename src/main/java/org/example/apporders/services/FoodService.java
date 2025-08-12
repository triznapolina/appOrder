package org.example.apporders.services;

import org.example.apporders.models.CategoriesFood;
import org.example.apporders.models.Food;

import java.math.BigDecimal;
import java.util.List;

public interface FoodService {

    List<Food> findByCategoryId(Long categoryId);

    List<Food> getAllFoods();

    Food findByName(String name);

    List<Food> findByPriceBetween(BigDecimal minRange, BigDecimal maxRange);



}
