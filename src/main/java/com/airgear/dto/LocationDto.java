package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
    private Long id;
    private Long regionId;
    private String settlement;
}
