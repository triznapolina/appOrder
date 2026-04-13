package com.RequestsDTO;

import lombok.Data;

@Data
public class DeliveryRequest {

    private Long id;
    private Boolean paymentType;
    private String timeDelivery;
    private Boolean isInplace;
    private String address;

}
