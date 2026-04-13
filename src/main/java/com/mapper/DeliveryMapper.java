package com.mapper;

import com.dto.OrderInfo;
import com.entity.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {

    OrderInfo.Delivery toDto(DeliveryEntity entity);

    DeliveryEntity toEntity(OrderInfo.Delivery delivery);
}
