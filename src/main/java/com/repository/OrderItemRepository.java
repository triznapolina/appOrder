package com.repository;

import com.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("select r from OrderItemEntity r where r.orderEntity.id = :orderId")
    List<OrderItemEntity> findByOrderId(Long orderId);

    @Query("select r from OrderItemEntity r where r.orderEntity.id = :orderId and r.foodEntity.id = :foodId")
    OrderItemEntity findByOrderAndFood(Long orderId, Long foodId);

    @Modifying
    @Query("UPDATE OrderItemEntity oi " +
            "SET oi.price = :price, oi.quantity = :quantity " +
            "WHERE oi.orderEntity.id = :orderId AND oi.foodEntity.id = :foodId")
    void updatePriceAndQuantityByOrderAndFood(@Param("price") BigDecimal price,
                                             @Param("quantity") Integer quantity,
                                             @Param("orderId") Long orderId,
                                             @Param("foodId") Long foodId);
}
