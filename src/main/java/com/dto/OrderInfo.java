package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInfo {

    private Long id;
    private Long clientId;
    private Long paymentId;
    private String status;
    private Boolean isCancelled;
    private Boolean isCompleted;
    private String shortDescription;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    private List<OrderItem> list;

    @Data
    public static class OrderItem {
        private Long id;
        private Long foodId;
        private Long orderId;
        private Integer quantity;
        private BigDecimal price;
    }

    private Delivery delivery;

    @Data
    public static class Delivery {

        private Long id;
        private Boolean byCard;
        private String address;
        private Long restaurantId;

    }

}
