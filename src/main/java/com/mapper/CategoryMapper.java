package com.mapper;

import com.entity.CategoryEntity;
import com.dto.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toDto(CategoryEntity entity);

    CategoryEntity toEntity(Category category);

}
