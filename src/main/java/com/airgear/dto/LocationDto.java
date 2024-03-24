package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

/**
 * LocationDto class. Fields are similar to Location entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */

@Data
@Builder
public class LocationDto {
    private Long id;
    private Long regionId;
    private String settlement;
}
