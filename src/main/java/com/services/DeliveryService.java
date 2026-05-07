package com.services;

import com.dto.OrderInfo;

public interface DeliveryService {

    OrderInfo.Delivery createDeliveryInfo(OrderInfo.Delivery delivery, Long orderId);

    OrderInfo.Delivery getDeliveryInfoOfOrder(Long orderId);

}
