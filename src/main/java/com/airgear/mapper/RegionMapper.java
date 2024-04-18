package com.airgear.mapper;

import com.airgear.dto.RegionDto;
import com.airgear.model.Region;
import com.airgear.dto.RegionResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionDto toDto(Region region);

    Region toModel(RegionDto dto);

    RegionResponseDTO toRegionResponseDTO(Region region);
}
