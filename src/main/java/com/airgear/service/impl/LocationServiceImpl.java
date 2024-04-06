package com.airgear.service.impl;

import com.airgear.exception.RegionExceptions;
import com.airgear.mapper.LocationMapper;
import com.airgear.mapper.RegionMapper;
import com.airgear.model.location.Location;
import com.airgear.dto.SaveLocationRequestDTO;
import com.airgear.dto.LocationResponseDTO;
import com.airgear.model.region.Region;
import com.airgear.dto.RegionResponseDTO;
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
    public LocationResponseDTO addLocation(SaveLocationRequestDTO request) {
        Location location = locationMapper.toEntity(request);
        location = locationRepository.save(location);
        return locationMapper.toLocationResponseDTO(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegionResponseDTO> getAllRegions(Pageable pageable) {
        return regionsRepository.findAll(pageable)
                .map(regionMapper::toRegionResponseDTO);
    }

    private Region getRegion(long regionId) {
        return regionsRepository.findById(regionId)
                .orElseThrow(() -> RegionExceptions.regionNotFound(regionId));
    }
}
