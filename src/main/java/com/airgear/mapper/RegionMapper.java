package com.airgear.mapper;

import com.airgear.dto.RegionRequest;
import com.airgear.dto.RegionResponse;
import com.airgear.model.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(source = "id", target = "regionId")
    @Mapping(source = "region", target = "regionName")
    RegionResponse toDto(Region region);

    @Mapping(source = "regionId", target = "id")
    @Mapping(source = "regionName", target = "region")
    Region toModel(RegionRequest dto);

}
