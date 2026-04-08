package com.repository;


import com.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Modifying
    @Query("UPDATE RestaurantEntity f SET f.address = :address, f.phone = :phone " +
            "WHERE f.id = :id")
    void updateRestaurantInfo(@Param("id") Long id, @Param("address") String address,
                    @Param("phone") String phone
    );


    RestaurantEntity findByAddress(String address);

}
