package com.airgear.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintCreateRequest {
    private UserGetRequest user;
    private ComplaintCategoryRequest complaintCategory;
    private GoodsGetRequest goods;
    private String description;
    private OffsetDateTime createdAt;
}


