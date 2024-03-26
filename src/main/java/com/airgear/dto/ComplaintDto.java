package com.airgear.dto;

import com.airgear.model.ComplaintCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintDto {
    private UserDto user;
    @JsonProperty("complaint_category")
    private ComplaintCategory complaintCategory;
    private GoodsDto goods;
    private String description;
    private OffsetDateTime createdAt;
}


