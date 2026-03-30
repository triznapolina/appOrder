package com.entity.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private Long id;
    private Long foodId;
    private Long orderId;
    private Integer quantity;
    private BigDecimal price;

}