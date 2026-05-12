package com.services.impl;

import com.dto.OrderInfo;
import com.entity.FoodEntity;
import com.entity.OrderEntity;
import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderItemRequest;
import com.mapper.OrderItemMapper;
import com.repository.FoodRepository;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import com.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final FoodRepository foodRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;


    @Transactional
    @Override
    public OrderInfo.OrderItem createItem(OrderItemRequest orderItemRequest) {
        Long orderId = orderItemRequest.getOrderId();
        Long foodId = orderItemRequest.getFoodId();
        Integer quantity = orderItemRequest.getQuantity();

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        FoodEntity foodEntity = foodRepository.findById(foodId).orElse(null);
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);

        orderItemEntity.setOrderEntity(orderEntity);
        orderItemEntity.setFoodEntity(foodEntity);
        orderItemEntity.setQuantity(quantity);

        orderItemRepository.save(orderItemEntity);

        OrderItemEntity saved = calculateAndSetTotalPrice(orderId, foodId, quantity);

        return orderItemMapper.toDto(saved);
    }


    @Transactional
    @Override
    public OrderInfo.OrderItem updateItem(OrderItemRequest request) {
        FoodEntity foodEntity = foodRepository.findById(request.getFoodId()).orElse(null);

        BigDecimal price = BigDecimal.ZERO;
        if (foodEntity.getPrice() != null && request.getQuantity() != null) {
            price = foodEntity.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        }

        orderItemRepository.updatePriceAndQuantityByOrderAndFood(price, request.getQuantity(), request.getOrderId(),
                request.getFoodId());

        recalculateOrderTotal(request.getOrderId());

        return orderItemMapper.toDto(orderItemRepository.findByOrderAndFood(request.getOrderId(), request.getFoodId()));
    }

    @Transactional
    @Override
    public void deleteItem(Long orderId, Long foodId) {
        orderItemRepository.delete(orderItemRepository.findByOrderAndFood(orderId, foodId));
        orderItemRepository.flush();
    }

    @Override
    public OrderInfo.OrderItem findById(Long orderItemId) {
        return orderItemMapper.toDto(orderItemRepository.findById(orderItemId).orElse(null));
    }


    OrderItemEntity calculateAndSetTotalPrice(Long orderId, Long foodId, Integer quantity) {

        OrderItemEntity orderItemEntity = orderItemRepository.findByOrderAndFood(orderId,foodId);
        FoodEntity foodEntity = foodRepository.findById(foodId).orElse(null);

        if (foodEntity.getPrice() != null && quantity != null) {
            BigDecimal totalPrice = foodEntity.getPrice().multiply(BigDecimal.valueOf(quantity));
            orderItemEntity.setPrice(totalPrice);
        } else {
            orderItemEntity.setPrice(BigDecimal.ZERO);
        }

        recalculateOrderTotal(orderId);

        return orderItemRepository.save(orderItemEntity);
    }

    public void recalculateOrderTotal(Long orderId) {

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(orderId);

        BigDecimal total = items.stream()
                .map(item -> item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = orderRepository.findById(orderId).orElseThrow();

        order.setTotalPrice(total);

        orderRepository.save(order);
    }

}
