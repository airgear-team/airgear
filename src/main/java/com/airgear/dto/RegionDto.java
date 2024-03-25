package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

/**
 * RegionDto class. Fields are similar to Region entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class RegionDto {
    private Long id;
    private String region;
}
