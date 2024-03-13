package com.airgear.mapper;

import com.airgear.dto.CategoryDto;
import com.airgear.model.goods.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toCategory(CategoryDto dto);
}
