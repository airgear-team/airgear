package com.airgear.mapper;

import com.airgear.dto.CategoryRequest;
import com.airgear.dto.CategoryResponse;
import com.airgear.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toDto(Category category);

    Category toModel(CategoryRequest dto);
}
