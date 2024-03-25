package com.airgear.dto;

import com.airgear.model.goods.enums.GoodsCondition;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * GoodsDto class. Fields are similar to Goods entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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
    private GoodsCondition goodsCondition;
}