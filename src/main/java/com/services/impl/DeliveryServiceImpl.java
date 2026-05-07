package com.services.impl;

import com.RequestsDTO.DeliveryRequest;
import com.dto.OrderInfo;
import com.entity.DeliveryEntity;
import com.entity.OrderEntity;
import com.entity.RestaurantEntity;
import com.mapper.DeliveryMapper;
import com.repository.DeliveryRepository;
import com.repository.OrderRepository;
import com.repository.RestaurantRepository;
import com.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public OrderInfo.Delivery createDeliveryInfo(OrderInfo.Delivery delivery, Long orderId) {

       DeliveryEntity deliveryEntity = deliveryMapper.toEntity(delivery);
       OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
       RestaurantEntity restaurant = restaurantRepository.findById(delivery.getRestaurantId()).orElse(null);

       deliveryEntity.setOrderEntity(orderEntity);
       deliveryEntity.setAddress(delivery.getAddress());
       deliveryEntity.setByCard(delivery.getByCard());
       deliveryEntity.setRestaurant(restaurant);
       deliveryRepository.save(deliveryEntity);

       return deliveryMapper.toDto(deliveryEntity);
    }


    @Override
    public OrderInfo.Delivery getDeliveryInfoOfOrder(Long orderId) {
        return deliveryMapper.toDto(deliveryRepository.findByOrderEntityId(orderId).orElse(null));
    }
}
