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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link LocationService} interface responsible
 * for handling location-related operations.
 * <p>
 *
 * @author Oleksandr Ilchenko, Oleksandr Tuleninov
 * @version 01
 * @see LocationService
 * @see RegionsRepository
 * @see LocationRepository
 * @see SaveLocationRequest
 * @see LocationResponse
 * @see Region
 * @see Location
 * @see Page
 * @see Pageable
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final RegionsRepository regionsRepository;
    private final LocationRepository locationRepository;

    public LocationServiceImpl(RegionsRepository regionsRepository,
                               LocationRepository locationRepository) {
        this.regionsRepository = regionsRepository;
        this.locationRepository = locationRepository;
    }

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
