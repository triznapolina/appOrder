package com.mapper;

import com.entity.FoodEntity;
import com.dto.Food;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodMapper {

    Food toDto(FoodEntity foodEntity);

    FoodEntity toEntity(Food food);

}
