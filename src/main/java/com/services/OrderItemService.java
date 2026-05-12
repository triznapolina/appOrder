package com.services;

import com.RequestsDTO.OrderItemRequest;
import com.dto.OrderInfo;

public interface OrderItemService {

    OrderInfo.OrderItem createItem(OrderItemRequest orderItem);

    OrderInfo.OrderItem updateItem(OrderItemRequest orderItem);

    void deleteItem(Long orderId, Long foodId);

    OrderInfo.OrderItem findById(Long orderItemId);

}
