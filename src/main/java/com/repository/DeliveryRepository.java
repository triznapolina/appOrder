package com.repository;

import com.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    @Query("SELECT f FROM DeliveryEntity f WHERE f.orderEntity.id = :orderId AND f.id = :deliveryId")
    DeliveryEntity findByDeliveryIdAndOrderId(@Param("deliveryId") Long deliveryId,
                                              @Param("orderId") Long orderId);

    Optional<DeliveryEntity> findByOrderEntityId(Long orderId);

}
