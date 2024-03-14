package com.airgear.service.impl;

import com.airgear.dto.LocationDto;
import com.airgear.dto.RegionDto;
import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;
import com.airgear.repository.LocationRepository;
import com.airgear.repository.RegionsRepository;
import com.airgear.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RegionsRepository regionsRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, RegionsRepository regionsRepository) {
        this.locationRepository = locationRepository;
        this.regionsRepository = regionsRepository;
    }

    @Override
    public List<RegionDto> getAllRegions() {
        return RegionDto.fromRegions(regionsRepository.findAll());
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
    public LocationDto addLocation(LocationDto locationDto) {
        Location newLocation = locationRepository.save(locationDto.toLocation());
        return LocationDto.fromLocation(newLocation);

    }
}
