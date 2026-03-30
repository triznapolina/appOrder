package com.entity.dto;

import lombok.Data;

@Data
public class Delivery {

    private Long id;
    private Boolean paymentType;
    private String timeDelivery;
    private Boolean isInplace;
    private String address;

}