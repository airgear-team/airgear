package com.airgear.dto;

import com.airgear.model.GoodsStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class GoodsGetRequest {
    private Long id;
    private String name;
    private String description;
    private PriceRequest price;
    private LocationRequest location;
    private GoodsStatus status;
    private Long userId;
}
