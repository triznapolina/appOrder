package com.services.impl;

import com.dto.OrderInfo;
import com.entity.FoodEntity;
import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderItemRequest;
import com.mapper.OrderItemMapper;
import com.repository.FoodRepository;
import com.repository.OrderItemRepository;
import com.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final FoodRepository foodRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;


    @Transactional
    @Override
    public OrderInfo.OrderItem createItem(OrderItemRequest orderItemRequest) {
        Long orderId = orderItemRequest.getOrderId();
        Long foodId = orderItemRequest.getFoodId();
        Integer quantity = orderItemRequest.getQuantity();

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

        return orderItemMapper.toDto(orderItemRepository.findByOrderAndFood(request.getOrderId(), request.getFoodId()));
    }

    @Override
    public void deleteItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
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

        return orderItemRepository.save(orderItemEntity);
    }


}
