package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

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
}
