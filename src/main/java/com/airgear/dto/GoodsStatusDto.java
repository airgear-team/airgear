package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

/**
 * GoodsStatusDto class. Fields are similar to GoodsStatus entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class GoodsStatusDto {
    private Long id;
    private String name;
}
