package com.airgear.service;

import com.airgear.dto.LocationResponse;
import com.airgear.dto.RegionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    Page<RegionResponse> getAllRegions(Pageable pageable);

    Page<LocationResponse> getAllLocationsByName(Pageable pageable, String name);

}
