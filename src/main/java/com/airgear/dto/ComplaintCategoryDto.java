package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintCategoryDto {
    private Long id;
    private String name;
}
