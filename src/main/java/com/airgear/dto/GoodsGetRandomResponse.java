package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsImages;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

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
    private List<GoodsImages> images;
}