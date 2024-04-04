package com.airgear.model.location.response;

import com.airgear.model.location.Location;

public record LocationResponse(Long id,
                               String settlement,
                               Long regionId) {

    public static LocationResponse fromLocation(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getSettlement(),
                location.getRegion().getId()
        );
    }
}
