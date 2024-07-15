package com.airgear.mapper;

import com.airgear.dto.LocationRequest;
import com.airgear.dto.LocationResponse;
import com.airgear.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RegionMapper.class)
public interface LocationMapper {

    @Mapping(source = "uniqueSettlementID", target = "locationId")
    @Mapping(source = "region.id", target = "regionId")
    LocationResponse toLocationResponse(Location location);

    @Mapping(source = "locationId", target = "uniqueSettlementID")
    Location toModel(LocationRequest dto);
}
