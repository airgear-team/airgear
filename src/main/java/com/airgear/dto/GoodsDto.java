package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GoodsDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal weekendsPrice;
    private LocationDto location;
    private CategoryDto category;
    private String phoneNumber;
    private UserDto user;
}