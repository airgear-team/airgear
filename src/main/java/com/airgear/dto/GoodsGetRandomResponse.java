package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class GoodsGetRandomResponse {

    private Long id;
    private String name;
    private PriceResponse price;
    private LocationResponse location;
    private OffsetDateTime createdAt;
    private GoodsCondition goodsCondition;
    private GoodsStatus status;
}