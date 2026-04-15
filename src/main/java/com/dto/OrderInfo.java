package com.dto;

import com.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInfo {

    private Long clientId;
    private Long restaurantId;
    private Long paymentId;
    private Long deliveryId;
    private String status;
    private Boolean isCancelled;
    private String shortDescription;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;

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
        private Boolean paymentType;
        private String timeDelivery;
        private Boolean isInplace;
        private String address;

    }

}
