package org.example.apporders.repositories;


import org.example.apporders.models.Food;
import org.example.apporders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId")
    List<Order> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT o FROM Order o where o.date = :foundDate")
    List<Order> findOrdersByDate(@Param("foundDate") String foundDate);


}
