package com.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Long clientId;
    private Long restaurantId;
    private Long paymentId;
    private Long deliveryId;
    private String status;
    private Boolean isCancelled;
    private String shortDescription;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;

}