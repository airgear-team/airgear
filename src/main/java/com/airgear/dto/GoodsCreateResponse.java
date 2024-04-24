package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
@Data
@Builder
public class GoodsCreateResponse {
    private Long id;
    private String name;
    private String description;
    private PriceDto price;
    private WeekendsPriceDto weekendsPrice;
    private DepositDto deposit;
    @Valid
    private LocationDto location;
    private CategoryDto category;
    private String phoneNumber;
    private UserDto user;
    private GoodsCondition goodsCondition;

    private GoodsStatus status;
}
