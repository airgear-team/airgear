package com.airgear.dto;

import com.airgear.model.goods.Category;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryDto class. Fields are similar to Category entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */

@Data
@Builder
public class CategoryDto {
    private Integer id;
    private String name;

    public Category toCategory() {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static CategoryDto fromCategory(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDto> fromCategories(List<Category> categories) {
        List<CategoryDto> result = new ArrayList<>();
        categories.forEach(category -> result.add(CategoryDto.fromCategory(category)));
        return result;
    }

}
