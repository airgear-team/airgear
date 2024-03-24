package com.airgear.service.impl;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;
import com.airgear.repository.LocationRepository;
import com.airgear.repository.RegionsRepository;
import com.airgear.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RegionsRepository regionsRepository;

    @Override
    public List<Region> getAllRegions() {
        return regionsRepository.findAll();
    }

    @Override
    public Location getLocationBySettlement(String settlement) {
        return locationRepository.findBySettlement(settlement);
    }

    @Override
    public Region getRegionById(Long id) {
        return regionsRepository.getReferenceById(id);
    }

    @Override
    public Location addLocation(Location location) {
        String settlement = location.getSettlement();
        Location existingLocation = getLocationBySettlement(settlement);
        if (existingLocation != null) {
            return existingLocation;
        } else {
            Location locationNew = new Location();
            locationNew.setSettlement(settlement);
            locationNew.setRegionId(location.getRegionId());
            locationRepository.save(locationNew);
            return locationNew;
        }
    }
}
