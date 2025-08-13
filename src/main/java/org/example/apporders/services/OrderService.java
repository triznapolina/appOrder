package org.example.apporders.services;

import org.example.apporders.models.Order;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderService {


    Order getOrder(Long id);

    void deleteOrder(Long id);


    Order createOrder(CreateOrderRequestDTO request);

    Order updateOrder(Long id, CreateOrderRequestDTO request);

    List<Order> getOrderbyDate(String date);


    List<Order> getAllOrdersForCurrentUser();


}
