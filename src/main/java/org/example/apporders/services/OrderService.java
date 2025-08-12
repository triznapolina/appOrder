package org.example.apporders.services;

import org.example.apporders.models.Order;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;

import java.util.List;

public interface OrderService {


    Order getOrder(Long id);

    void deleteOrder(Long id);


    Order createOrder(CreateOrderRequestDTO request);


    List<Order> getAllOrdersForCurrentUser();
}
