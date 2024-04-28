package com.airgear.service;

import com.airgear.dto.LocationRequest;
import com.airgear.dto.LocationResponse;
import com.airgear.dto.LocationSaveRequest;
import com.airgear.dto.RegionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    LocationResponse addLocation(LocationSaveRequest request);

    Page<RegionResponse> getAllRegions(Pageable pageable);

}
