package com.airgear.service.impl;

import com.airgear.dto.RegionResponse;
import com.airgear.exception.RegionExceptions;
import com.airgear.mapper.RegionMapper;
import com.airgear.model.Region;
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

    private final RegionsRepository regionsRepository;
    private final RegionMapper regionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RegionResponse> getAllRegions(Pageable pageable) {
        return regionsRepository.findAll(pageable)
                .map(regionMapper::toDto);
    }

    private Region getRegion(long regionId) {
        return regionsRepository.findById(regionId)
                .orElseThrow(() -> RegionExceptions.regionNotFound(regionId));
    }
}
