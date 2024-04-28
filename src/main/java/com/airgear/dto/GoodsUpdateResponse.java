package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsUpdateResponse {

    private Long id;
    private String name;
    private String description;
    private PriceResponse price;
    private WeekendsPriceResponse weekendsPrice;
    private DepositResponse deposit;
    private LocationResponse location;
    private CategoryResponse category;
    private String phoneNumber;
    private UserGetResponse user;
    private GoodsCondition goodsCondition;

    private GoodsStatus status;
}