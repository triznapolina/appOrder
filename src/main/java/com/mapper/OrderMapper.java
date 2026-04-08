package com.mapper;

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

    Order toDto(OrderEntity orderEntity);

    OrderEntity toEntity(Order order);

    OrderEntity requestToEntity(OrderRequest orderRequest);

    @Mapping(target = "paymentId", source = "paymentEntity.id")
    @Mapping(target = "deliveryId", source = "delivery.id")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "restaurantId", source = "restaurantEntity.id")
    OrderInfo toDtoInfo(OrderEntity orderEntity, List<OrderItemEntity> orderItemList);

}
