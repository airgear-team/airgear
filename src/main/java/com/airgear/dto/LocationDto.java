package com.airgear.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
    private Integer locationId;
    private String settlement;
    private RegionDto region;
}
