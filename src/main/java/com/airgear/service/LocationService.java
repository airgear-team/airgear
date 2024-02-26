package com.airgear.service;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;

import java.util.HashMap;
import java.util.List;

public interface LocationService {
    Location addLocation(Location location);
    List<Region> getAllRegions();

}
