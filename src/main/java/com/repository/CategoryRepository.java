package com.repository;

import com.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Modifying
    @Query("UPDATE CategoryEntity f SET f.name = :name, f.shortDescription = :shortDescription " +
            "WHERE f.id = :id")
    void updateCategoryById(@Param("id") Long id, @Param("name") String name, @Param("shortDescription") String shortDescription);


}
