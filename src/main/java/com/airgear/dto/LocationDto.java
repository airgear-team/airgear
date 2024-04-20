package com.airgear.dto;

import com.airgear.model.Region;
import com.airgear.model.SettlementType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
    private Integer uniqueId;
    private String settlement;
    private RegionDto region;
}
