package com.airgear.model.region.response;

import com.airgear.model.region.Region;

public record RegionResponse(Long id,
                             String region) {

    public static RegionResponse fromRegion(Region region) {
        return new RegionResponse(
                region.getId(),
                region.getRegion()
        );
    }
}
