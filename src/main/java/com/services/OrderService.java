package com.services;

import org.example.apporders.entity.RequestsDTO.CreateOrderRequestDTO;

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
