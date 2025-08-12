package org.example.apporders.services.impl;

import org.example.apporders.models.Food;
import org.example.apporders.repositories.FoodRepository;
import org.example.apporders.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository menuRepository;


    @Override
    public List<Food> getAllFoods(){
        return menuRepository.findAll();
    }

    @Override
    public Food findByName(String name) {
        return menuRepository.findFoodByName(name);
    }


    //filter
    @Override
    public List<Food> findByPriceBetween(BigDecimal minRange, BigDecimal maxRange) {
        return menuRepository.findFoodByPrice(minRange, maxRange);
    }


    @Override
    public List<Food> findByCategoryId(Long categoryId) {
        return menuRepository.findByCategoryId(categoryId);
    }


}
