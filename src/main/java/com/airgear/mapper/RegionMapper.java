package com.airgear.mapper;

import com.airgear.dto.RegionDto;
import com.airgear.model.goods.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionDto toDto(Region region);

    Region toRegion(RegionDto dto);
}
