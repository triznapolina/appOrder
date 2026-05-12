package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Long clientId;
    private Integer number;
    private String status;
    private Boolean isDeleted;
    private Boolean isCompleted;
    private String shortDescription;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

}