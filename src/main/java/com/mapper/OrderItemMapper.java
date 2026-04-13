package com.mapper;

import com.dto.OrderInfo;
import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    OrderInfo.OrderItem toDto(OrderItemEntity orderItemEntity);

    OrderItemEntity toEntity(OrderInfo.OrderItem orderItem);

    OrderItemEntity toEntity(OrderItemRequest orderItem);
}
