package com.airgear.mapper;

import com.airgear.dto.RegionDto;
import com.airgear.model.Region;
import com.airgear.dto.RegionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(source = "id", target = "regionId")
    @Mapping(source = "region", target = "regionName")
    RegionDto toDto(Region region);

    @Mapping(source = "regionId", target = "id")
    @Mapping(source = "regionName", target = "region")
    Region toModel(RegionDto dto);

    RegionResponseDTO toRegionResponseDTO(Region region);
}
