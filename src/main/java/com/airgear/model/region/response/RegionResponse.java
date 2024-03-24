package com.airgear.model.region.response;

import com.airgear.model.region.Region;

/**
 * Represents a response object for location with specific fields.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Region
 */
public record RegionResponse(Long id,
                             String region) {

    /**
     * Creates a new {@code RegionResponse} instance based on the provided {@code Region} entity.
     *
     * @param region the source region entity.
     * @return a new {@code LocationResponse} instance with data from the given location.
     */
    public static RegionResponse fromRegion(Region region) {
        return new RegionResponse(
                region.getId(),
                region.getRegion()
        );
    }
}
