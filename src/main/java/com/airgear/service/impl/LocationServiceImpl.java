package com.airgear.service.impl;

import com.airgear.exception.RegionExceptions;
import com.airgear.model.location.Location;
import com.airgear.model.location.request.SaveLocationRequest;
import com.airgear.model.location.response.LocationResponse;
import com.airgear.model.region.Region;
import com.airgear.model.region.response.RegionResponse;
import com.airgear.repository.LocationRepository;
import com.airgear.repository.RegionsRepository;
import com.airgear.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RegionsRepository regionsRepository;

    @Override
    public LocationResponse addLocation(SaveLocationRequest request) {
        return LocationResponse.fromLocation(save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegionResponse> getAllRegions(Pageable pageable) {
        return regionsRepository.findAll(pageable)
                .map(RegionResponse::fromRegion);
    }

    private Region getRegion(long regionId) {
        return regionsRepository.findById(regionId)
                .orElseThrow(() -> RegionExceptions.regionNotFound(regionId));
    }

    private Location save(SaveLocationRequest request) {
        Region region = getRegion(request.region_id());

        Location location = new Location(
                request.settlement(),
                region
        );
        locationRepository.save(location);
        return location;
    }
}
