package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsImages;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class GoodsCreateResponse {

    private Long id;
    private String name;
    private String description;
    private PriceResponse price;
    private WeekendsPriceResponse weekendsPrice;
    private DepositResponse deposit;
    @Valid
    private LocationResponse location;
    private CategoryResponse category;
    private String phoneNumber;
    private UserGetResponse user;
    private GoodsCondition goodsCondition;
    private OffsetDateTime createdAt;
    private GoodsStatus status;
    private String sourceUrl;
    private List<GoodsImagesResponse> images;
}