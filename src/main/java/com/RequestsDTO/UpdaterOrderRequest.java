package com.RequestsDTO;

import com.dto.OrderInfo;
import lombok.Data;

@Data
public class UpdaterOrderRequest {

    private Long paymentId;
    private OrderInfo.Delivery delivery;
    private String shortDescription;

}
