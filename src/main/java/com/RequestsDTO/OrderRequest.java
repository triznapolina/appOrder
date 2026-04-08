package com.RequestsDTO;

import lombok.Data;


@Data
public class OrderRequest {

    private Long clientId;
    private Long restaurantId;
    private Long paymentId;
    private Long deliveryId;
    private String shortDescription;

}
