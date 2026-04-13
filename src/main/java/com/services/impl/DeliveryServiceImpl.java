package com.services.impl;

import com.RequestsDTO.DeliveryRequest;
import com.dto.OrderInfo;
import com.entity.DeliveryEntity;
import com.entity.OrderEntity;
import com.mapper.DeliveryMapper;
import com.repository.DeliveryRepository;
import com.repository.OrderRepository;
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

    @Transactional
    @Override
    public OrderInfo.Delivery createDeliveryInfo(OrderInfo.Delivery delivery, Long orderId) {

       DeliveryEntity deliveryEntity = deliveryMapper.toEntity(delivery);
       OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);

       deliveryEntity.setOrder(orderEntity);
       deliveryEntity.setTimeDelivery(delivery.getTimeDelivery());
       deliveryEntity.setAddress(delivery.getAddress());
       deliveryEntity.setPaymentType(delivery.getPaymentType());
       deliveryEntity.setIsInplace(delivery.getIsInplace());
       deliveryRepository.save(deliveryEntity);

       return deliveryMapper.toDto(deliveryEntity);
    }


    @Override
    public OrderInfo.Delivery getDeliveryInfoOfOrder(Long orderId, Long deliveryId) {
        return deliveryMapper.toDto(deliveryRepository.findByDeliveryIdAndOrderId(deliveryId, orderId));
    }
}
