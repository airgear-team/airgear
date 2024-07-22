package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsImages;
import com.airgear.model.GoodsStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class GoodsGetResponse {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastModified;
    private LocationResponse location;
    private CategoryRequest category;
    private WeekendsPriceRequest weekendsPrice;
    private GoodsCondition goodsCondition;
    private GoodsStatus status;
    private List<GoodsImagesResponse> images;
    private DepositRequest deposit;
    private PriceResponse price;
    private String phoneNumber;
}
