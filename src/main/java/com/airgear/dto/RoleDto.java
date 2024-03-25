package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

/**
 * RoleDto class. Fields are similar to Role entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private String description;
}
