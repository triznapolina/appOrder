package org.example.apporders.repositories;

import org.example.apporders.models.CategoriesFood;
import org.example.apporders.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Food findFoodByName(String food_name);

    @Query("SELECT f FROM Food f WHERE f.category.id = :categoryId")
    List<Food> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT f FROM Food f where f.price between :minRange and :maxRange")
    List<Food> findFoodByPrice(@Param("minRange") BigDecimal minRange,
                               @Param("maxRange") BigDecimal maxRange);


}
