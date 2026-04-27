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

    FoodEntity toEntity(Food food);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToString")
    Food toDto(FoodEntity foodEntity);

    @Named("localDateTimeToDate")
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
