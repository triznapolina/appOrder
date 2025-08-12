package org.example.apporders.services;

import org.example.apporders.models.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @Test
    void getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        assertNotNull(foods, "not null");

    }

    @Test
    void findByName() {

        Food foundFood = foodService.findByName("Sushi");
        assertNotNull(foundFood, "not null");


    }

    @Test
    void findByPriceBetween() {
        List<Food> foods = foodService.findByPriceBetween(BigDecimal.valueOf(7.00), BigDecimal.valueOf(27.00));
        assertNotNull(foods, "not null");

    }

    @Test
    void findByCategoryId() {
        List<Food> foods = foodService.findByCategoryId(4L);
        assertNotNull(foods, "not null");


    }
}
