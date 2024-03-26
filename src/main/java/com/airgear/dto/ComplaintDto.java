package com.airgear.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintDto {
    private UserDto user;
    private String complaintCategory;
    private GoodsDto goods;
    private String description;
    private OffsetDateTime createdAt;
}


