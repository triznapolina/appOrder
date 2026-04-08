package com.mapper;

import com.entity.RestaurantEntity;
import com.dto.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {

    Restaurant toDto(RestaurantEntity restaurantEntity);

    RestaurantEntity toEntity(Restaurant restaurant);


}
