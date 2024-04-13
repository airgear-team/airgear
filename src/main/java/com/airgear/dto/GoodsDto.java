package com.airgear.dto;

import com.airgear.model.goods.Price;
import com.airgear.model.goods.WeekendsPrice;
import com.airgear.model.goods.enums.GoodsCondition;
import com.airgear.model.goods.enums.GoodsStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsDto {
    private Long id;
    private String name;
    private String description;
    private Price price;
    private WeekendsPrice weekendsPrice;
    private LocationDto location;
    private CategoryDto category;
    private String phoneNumber;
    private UserDto user;
    private GoodsCondition goodsCondition;
    private GoodsStatus status;
}