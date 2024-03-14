package com.airgear.service;

import com.airgear.dto.LocationDto;
import com.airgear.dto.RegionDto;
import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;

import java.util.List;

public interface LocationService {

    LocationDto addLocation(LocationDto locationDto);
    Location getLocationBySettlement(String settlement);
    List<RegionDto> getAllRegions();
    Region getRegionById(Long id);

}
