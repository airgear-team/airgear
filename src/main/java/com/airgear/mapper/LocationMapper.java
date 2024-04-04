package com.airgear.mapper;

import com.airgear.dto.LocationDto;
import com.airgear.model.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "regionId", source = "region.id")
    LocationDto toDto(Location location);

    Location toModel(LocationDto dto);
}
