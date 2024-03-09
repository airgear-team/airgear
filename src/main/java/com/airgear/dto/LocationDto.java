package com.airgear.dto;

import com.airgear.model.goods.Location;
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
    private String settlement;
    private Long regionId;

    public Location toLocation() {
        return Location.builder()
                .id(id)
                .settlement(settlement)
                .regionId(regionId)
                .build();
    }

    public static LocationDto fromLocation(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .settlement(location.getSettlement())
                .regionId(location.getRegionId())
                .build();
    }

    public static List<LocationDto> fromLocations(List<Location> locations) {
        List<LocationDto> result = new ArrayList<>();
        locations.forEach(location -> result.add(LocationDto.fromLocation(location)));
        return result;
    }
}
