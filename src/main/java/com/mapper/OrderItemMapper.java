package com.mapper;

import com.entity.OrderItemEntity;
import com.RequestsDTO.OrderItemRequest;
import com.dto.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    OrderItem toDto(OrderItemEntity orderItemEntity);

    OrderItemEntity toEntity(OrderItem orderItem);

    OrderItemEntity toEntity(OrderItemRequest orderItem);
}
