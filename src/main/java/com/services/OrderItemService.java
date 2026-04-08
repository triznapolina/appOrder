package com.services;

import com.RequestsDTO.OrderItemRequest;
import com.dto.OrderItem;

public interface OrderItemService {

    OrderItem createItem(OrderItemRequest orderItem);

    OrderItem updateItem(OrderItemRequest orderItem);

    void deleteItem(Long orderItemId);

    OrderItem findById(Long orderItemId);

}
