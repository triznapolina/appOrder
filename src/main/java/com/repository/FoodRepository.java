package com.repository;

import com.entity.CategoryEntity;
import com.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    @Modifying
    @Query("UPDATE FoodEntity f SET f.name = :name, f.shortDescription = :shortDescription, " +
            "f.price = :price, f.categoryEntity.id = :categoryId " +
            "WHERE f.id = :foodId")
    void updateFood( @Param("foodId") Long foodId, @Param("name") String name,
            @Param("shortDescription") String shortDescription, @Param("price") BigDecimal price,
            @Param("categoryId") Long categoryId);

    List<FoodEntity> findFoodByName(String foodName);

    List<FoodEntity> findByCategoryEntityId(Long categoryId);

    @Query("""
          SELECT f FROM FoodEntity f
          WHERE f.price BETWEEN :minRange AND :maxRange
          AND f.categoryEntity.id = :categoryId
    """)
    List<FoodEntity> findFoodByPriceAndCategory(
            @Param("categoryId") Long categoryId,
            @Param("minRange") BigDecimal minRange,
            @Param("maxRange") BigDecimal maxRange
    );

    List<FoodEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

}
