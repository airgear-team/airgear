package com.airgear.service.impl;

import com.airgear.dto.LocationResponse;
import com.airgear.dto.RegionResponse;
import com.airgear.mapper.LocationMapper;
import com.airgear.mapper.RegionMapper;
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
    private final LocationMapper locationMapper;
    private final RegionMapper regionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RegionResponse> getAllRegions(Pageable pageable) {
        return regionsRepository.findAll(pageable)
                .map(regionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocationResponse> getAllLocationsByName(Pageable pageable, String name) {
        return locationRepository.findAllBySettlementContainingIgnoreCase(pageable, name)
                .map(locationMapper::toLocationResponse);
    }
}
