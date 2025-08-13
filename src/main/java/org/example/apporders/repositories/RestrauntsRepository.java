package org.example.apporders.repositories;

import org.example.apporders.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestrauntsRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByAddress(String address);

    Restaurant getRestaurantById(Long restaurantId);
}
