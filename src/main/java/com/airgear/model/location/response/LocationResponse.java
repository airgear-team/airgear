package com.airgear.model.location.response;

import com.airgear.model.location.Location;

/**
 * Represents a response object for location with specific fields.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Location
 */
public record LocationResponse(Long id,
                               String settlement,
                               Long regionId) {

    /**
     * Creates a new {@code LocationResponse} instance based on the provided {@code Location} entity.
     *
     * @param location the source region entity.
     * @return a new {@code LocationResponse} instance with data from the given location.
     */
    public static LocationResponse fromLocation(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getSettlement(),
                location.getRegion().getId()
        );
    }
}
