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
    private Long regionId;
    private String settlement;

    public Location toLocation() {
        return Location.builder()
                .id(id)
                .settlement(settlement)
                .build();
    }

    public static LocationDto fromLocation(Location location) {
        return com.airgear.dto.LocationDto.builder()
                .id(location.getId())
                .settlement(location.getSettlement())
                .build();
    }

    public static List<LocationDto> fromLocations(List<Location> locations) {
        List<LocationDto> result = new ArrayList<>();
        locations.forEach(location -> result.add(com.airgear.dto.LocationDto.fromLocation(location)));
        return result;
    }
}
