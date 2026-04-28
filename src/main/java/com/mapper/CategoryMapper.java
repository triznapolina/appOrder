package com.mapper;

import com.entity.CategoryEntity;
import com.dto.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "shortDescription", source = "shortDescription")
    Category toDto(CategoryEntity entity);

    @Mapping(target = "shortDescription", source = "shortDescription")
    CategoryEntity toEntity(Category category);

}
