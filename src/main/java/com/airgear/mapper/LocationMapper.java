package com.airgear.mapper;

import com.airgear.dto.LocationDto;
import com.airgear.model.Location;
import com.airgear.dto.SaveLocationRequestDTO;
import com.airgear.dto.LocationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(source = "uniqueSettlementID", target = "uniqueId")
    LocationDto toDto(Location location);

    @Mapping(target = "region", ignore = true)
    Location toEntity(SaveLocationRequestDTO dto);

    @Mapping(source = "region.id", target = "regionId")
    LocationResponseDTO toLocationResponseDTO(Location location);

    Location toModel(LocationDto dto);
}
