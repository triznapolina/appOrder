package org.example.apporders.repositories;

import org.example.apporders.models.Restraunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestrauntsRepository extends JpaRepository<Restraunt, Long> {

    Restraunt findByAddress(String address);

    Restraunt getRestaurantById(Long restaurantId);
}
