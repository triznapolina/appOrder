package com.RequestsDTO;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long foodId;
    private Long orderId;
    private Integer quantity;
}
