package com.airgear.service.impl;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;
import com.airgear.repository.LocationRepository;
import com.airgear.repository.RegionRepository;
import com.airgear.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, RegionRepository regionRepository) {
        this.locationRepository = locationRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region getRegionById(Long id) {
        return regionRepository.getReferenceById(id);
    }

    @Override
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }
}
