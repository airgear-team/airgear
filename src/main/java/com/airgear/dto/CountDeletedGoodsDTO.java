package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class CountDeletedGoodsDTO {
    private String categoryName;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Long countDeletedGoods;
}