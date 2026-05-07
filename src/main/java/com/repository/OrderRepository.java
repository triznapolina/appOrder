package com.repository;

import com.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.client.id = :clientId")
    List<OrderEntity> findByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Query("UPDATE OrderEntity f SET f.restaurantEntity.id = :restaurantId,f.isCompleted = :isCompleted, " +
            "f.shortDescription = :shortDescription, f.number = :number " +
            "WHERE f.id = :orderId")
    void updateOrder(@Param("orderId") Long orderId, @Param("restaurantId") Long restaurantId, @Param("number") Integer number,
                     @Param("shortDescription") String shortDescription,@Param("isCompleted") Boolean isCompleted);


    @Query("SELECT o FROM OrderEntity o WHERE DATE(o.createdAt) = :date")
    List<OrderEntity> findByDateCreatedAt(@Param("date") LocalDate date);


    @Modifying
    @Query("update OrderEntity f set f.status = :status where f.id = :orderId")
    void setStatus(Long orderId, String status);

    @Modifying
    @Query("update OrderEntity f set f.isCancelled = :isCancelled where f.id = :orderId")
    void cancelled(Long orderId, boolean isCancelled);

    OrderEntity findByClientIdAndIsCompletedFalse(Long clientId);

    boolean existsByNumber(int createdNumber);
}
