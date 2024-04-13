package com.airgear.dto;

import com.airgear.model.goods.enums.GoodsCondition;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

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
    private Set<UserDto> usersAddedToFavorite;
    private GoodsCondition goodsCondition;
}