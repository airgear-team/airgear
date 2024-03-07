package com.airgear.service;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;

import java.util.List;

public interface LocationService {

    Location addLocation(Location location);
    Location getLocationBySettlement(String settlement);
    List<Region> getAllRegions();
    Region getRegionById(Long id);

}
