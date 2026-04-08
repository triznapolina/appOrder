package com.services;

import com.RequestsDTO.OrderRequest;
import com.dto.Order;
import com.dto.OrderInfo;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderInfo getOrder(Long id);

    void deleteOrder(Long id);

    OrderInfo createOrder(OrderRequest order);

    OrderInfo updateOrder(Long id, OrderRequest request);

    List<Order> getOrderByCreated(LocalDate date);

    List<Order> getOrderByClientId(Long id);

    void cancelledOrder(Long id, boolean type);

    Order updateStatusOrder(Long id, String status);

    OrderInfo updateTotalPriceInOrder(Long id);

    // only admin
    Page<Order> getAllOrders(FilterRequest filterRequest);
}
