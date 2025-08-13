package org.example.apporders.services;

import org.example.apporders.models.Order;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {


    Order getOrder(Long id);

    void deleteOrder(Long id);


    void createOrder(CreateOrderRequestDTO request);

    void updateOrder(Long id, CreateOrderRequestDTO request);

    List<Order> getOrderbyDate(LocalDate date);


    List<Order> getAllOrdersForCurrentUser();


}
