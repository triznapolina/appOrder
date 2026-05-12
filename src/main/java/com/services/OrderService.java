package com.services;

import com.RequestsDTO.OrderRequest;
import com.RequestsDTO.UpdaterOrderRequest;
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

    OrderInfo updateOrder(Long id, UpdaterOrderRequest request);

    List<Order> getOrderByCreated(LocalDate date);

    List<Order> getOrderByClientId(Long id);

    void cancelledOrder(Long id, boolean type);

    Order updateStatusOrder(Long id, String status);

    OrderInfo updateTotalPriceInOrder(Long id);

    Page<Order> getAllOrders(FilterRequest filterRequest);

    Order getOrderIsNotCompletedByClientId(Long clientId);

    List<Order> getOrdersByStatus(Long clientId, String status);

    void updateIsDeleted(Long id, Boolean status);
}
