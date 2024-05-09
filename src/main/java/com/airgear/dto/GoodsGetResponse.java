package com.airgear.dto;

import com.airgear.model.GoodsImages;
import com.airgear.model.GoodsStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class GoodsGetResponse {
    private Long id;
    private String name;
    private String description;
    private PriceResponse price;
    private LocationResponse location;
    private GoodsStatus status;
    private Long userId;
    private List<GoodsImagesResponse> images;
}
