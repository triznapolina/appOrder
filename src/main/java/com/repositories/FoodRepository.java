package com.repositories;

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
            @Param("categoryId") Long categoryId
    );

    @Modifying
    @Query("update FoodEntity f set f.isDeleted = :status where f.id = :foodId")
    void setIsDeleted(@Param("foodId") Long foodId, boolean status);

    @Modifying
    @Query("update FoodEntity f set f.isActive = :status where f.id = :foodId")
    void setIsActive(@Param("foodId") Long foodId, boolean status);

    List<FoodEntity> findFoodByName(String foodName);

    @Query("SELECT f FROM FoodEntity f WHERE f.category.id = :categoryId")
    List<FoodEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT f FROM FoodEntity f where f.price between :minRange and :maxRange")
    List<FoodEntity> findFoodByPrice(@Param("minRange") BigDecimal minRange,
                                     @Param("maxRange") BigDecimal maxRange);


}
