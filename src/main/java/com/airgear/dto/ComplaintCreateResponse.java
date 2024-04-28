package com.airgear.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintCreateResponse {
    private UserGetResponse user;
    private ComplaintCategoryResponse complaintCategoryResponse;
    private GoodsGetResponse goods;
    private String description;
    private OffsetDateTime createdAt;
}


