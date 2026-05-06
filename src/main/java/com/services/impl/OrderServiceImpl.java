package com.services.impl;

import com.RequestsDTO.UpdaterOrderRequest;
import com.dto.Client;
import com.entity.ClientEntity;
import com.entity.DeliveryEntity;
import com.entity.OrderEntity;
import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderRequest;
import com.dto.Order;
import com.dto.OrderInfo;
import com.inHead.FilterRequest;
import com.mapper.OrderMapper;
import com.repository.ClientRepository;
import com.repository.DeliveryRepository;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import com.services.JwtService;
import com.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public OrderInfo createOrder(OrderRequest order) {
        OrderEntity entity = orderMapper.requestToEntity(order);

        ClientEntity client = clientRepository.findById(order.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        entity.setClient(client);

        entity.setStatus("CREATED");
        entity.setIsCancelled(false);
        entity.setIsCompleted(false);

        OrderEntity savedEntity = orderRepository.save(entity);

        OrderEntity orderEntity = calculateAndSetTotalPrice(savedEntity.getId());
        List<OrderItemEntity> list = orderItemRepository.findByOrderId(savedEntity.getId());

        DeliveryEntity delivery = deliveryRepository.findByOrderId(savedEntity.getId());

        return orderMapper.toDtoInfo(orderEntity, list, delivery);
    }


    @Transactional
    @Override
    public OrderInfo updateOrder(Long id, UpdaterOrderRequest request) {
        orderRepository.updateOrder(id, request.getDelivery().getRestaurantId(), request.getShortDescription(), true);
        orderRepository.setStatus(id, "IN COOKING PROCESS");

        OrderEntity orderEntity = calculateAndSetTotalPrice(id);
        List<OrderItemEntity> list = orderItemRepository.findByOrderId(id);

        DeliveryEntity delivery = deliveryRepository.findByOrderId(orderEntity.getId());

        return orderMapper.toDtoInfo(orderEntity, list, delivery);
    }


    @Override
    public OrderInfo getOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);
        List<OrderItemEntity> list = orderItemRepository.findByOrderId(id);
        DeliveryEntity delivery = deliveryRepository.findByOrderId(orderEntity.getId());

        return orderMapper.toDtoInfo(orderEntity, list, delivery);
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {

        List<OrderItemEntity> orderItemsToDelete = orderItemRepository.findByOrderId(orderId);
        DeliveryEntity delivery = deliveryRepository.findByOrderId(orderId);
        if (!orderItemsToDelete.isEmpty() && delivery != null) {
            orderItemRepository.deleteAll(orderItemsToDelete);
            deliveryRepository.delete(delivery);
        }

        orderRepository.deleteById(orderId);
    }

    @Override
    public void cancelledOrder(Long id, boolean type) {
        orderRepository.cancelled(id, type);
    }

    @Transactional
    @Override
    public Order updateStatusOrder(Long id, String status) {

        orderRepository.setStatus(id, status);

        return orderMapper.toDto(orderRepository.findById(id).orElse(null));
    }

    @Override
    public OrderInfo updateTotalPriceInOrder(Long orderId) {

        OrderEntity orderEntity = calculateAndSetTotalPrice(orderId);
        List<OrderItemEntity> list = orderItemRepository.findByOrderId(orderId);
        DeliveryEntity delivery = deliveryRepository.findByOrderId(orderId);

        return orderMapper.toDtoInfo(orderEntity, list, delivery);
    }

    @Override
    public List<Order> getOrderByCreated(LocalDate date) {
        return orderRepository.findByDateCreatedAt(date).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<Order> getOrderByClientId(Long id) {
        return orderRepository.findByClientId(id).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public Page<Order> getAllOrders(FilterRequest request) {
        return orderRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(orderMapper::toDto);
    }

    @Override
    public Order getOrderIsNotCompletedByClientId(Long clientId) {
        return orderMapper.toDto(orderRepository.findByClientIdAndIsCompletedFalse(clientId));
    }

    OrderEntity calculateAndSetTotalPrice(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElse(null);

        List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderId(orderId);

        BigDecimal totalPrice = orderItemEntities.stream()
                .map(OrderItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }


}
