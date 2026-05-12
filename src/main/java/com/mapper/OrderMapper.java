package com.mapper;

import com.entity.DeliveryEntity;
import com.entity.OrderEntity;
import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderRequest;
import com.dto.Order;
import com.dto.OrderInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "clientId", source = "orderEntity.client.id")
    @Mapping(target = "isDeleted", source = "isDeleted")
    Order toDto(OrderEntity orderEntity);

    OrderEntity toEntity(Order order);

    OrderEntity requestToEntity(OrderRequest orderRequest);

    @Mapping(target = "id", source = "orderEntity.id")
    @Mapping(target = "paymentId", source = "orderEntity.paymentEntity.id")
    @Mapping(target = "clientId", source = "orderEntity.client.id")
    @Mapping(target = "list", source = "orderItemList")
    @Mapping(target = "delivery", source = "delivery")
    @Mapping(target = "number", source = "orderEntity.number")
    @Mapping(target = "isDeleted", source = "orderEntity.isDeleted")
    @Mapping(target = "createdAt", source = "orderEntity.createdAt")
    OrderInfo toDtoInfo(OrderEntity orderEntity, List<OrderItemEntity> orderItemList, DeliveryEntity delivery);

    @Mapping(target = "foodId", source = "foodEntity.id")
    @Mapping(target = "orderId", source = "orderEntity.id")
    OrderInfo.OrderItem toOrderItemDto(OrderItemEntity entity);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    OrderInfo.Delivery toDeliveryDto(DeliveryEntity entity);
}
