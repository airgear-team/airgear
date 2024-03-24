package com.airgear.mapper;

import com.airgear.dto.LocationDto;
import com.airgear.model.location.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location location);

    Location toModel(LocationDto dto);
}
