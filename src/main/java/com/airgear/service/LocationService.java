package com.airgear.service;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Regions;

import java.util.List;

public interface LocationService {

    Location addLocation(Location location);
    Location getLocationBySettlement(String settlement);
    List<Regions> getAllRegions();
    Regions getRegionById(Long id);

}
