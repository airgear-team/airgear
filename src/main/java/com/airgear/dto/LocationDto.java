package com.airgear.dto;

import com.airgear.model.location.Location;
import com.airgear.model.region.Region;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * LocationDto class. Fields are similar to Location entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */

@Data
@Builder
public class LocationDto {
    private Long id;
    private Region region;
    private String settlement;

    public Location toLocation() {
        return Location.builder()
                .id(id)
                .settlement(settlement)
                .region(region)
                .build();
    }

    public static LocationDto fromLocation(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .settlement(location.getSettlement())
                .region(location.getRegion())
                .build();
    }

    public static List<LocationDto> fromLocations(List<Location> locations) {
        List<LocationDto> result = new ArrayList<>();
        locations.forEach(location -> result.add(LocationDto.fromLocation(location)));
        return result;
    }
}
