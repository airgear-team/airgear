package com.airgear.dto;

import com.airgear.model.region.Region;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * RegionDto class. Fields are similar to Region entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class RegionDto {

    private Long id;
    private String region;

    public Region toRegion() {
        return Region.builder()
                .id(id)
                .region(region)
                .build();
    }

    public static List<Region> toRegions(List<RegionDto> regions) {
        List<Region> result = new ArrayList<>();
        regions.forEach(region -> result.add(region.toRegion()));
        return result;
    }

    public static RegionDto fromRegion(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .region(region.getRegion())
                .build();
    }

    public static List<RegionDto> fromRegions(List<Region> regions) {
        List<RegionDto> result = new ArrayList<>();
        regions.forEach(region -> result.add(RegionDto.fromRegion(region)));
        return result;
    }

}
