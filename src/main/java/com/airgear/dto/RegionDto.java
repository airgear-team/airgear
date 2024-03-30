package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDto {
    private Long id;
    private String region;
}
