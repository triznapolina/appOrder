package com.mapper;

import com.entity.FoodEntity;
import com.dto.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodMapper {

    @Mapping(target = "categoryEntity.id", source = "categoryId")
    FoodEntity toEntity(Food food);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToDate")
    @Mapping(target = "categoryId", source = "categoryEntity.id")
    Food toDto(FoodEntity foodEntity);

    @Named("localDateTimeToDate")
    static Date localDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
