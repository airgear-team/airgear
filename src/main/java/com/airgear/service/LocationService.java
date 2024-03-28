package com.airgear.service;

import com.airgear.model.location.request.SaveLocationRequest;
import com.airgear.model.location.response.LocationResponse;
import com.airgear.model.region.response.RegionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    LocationResponse addLocation(SaveLocationRequest request);

    Page<RegionResponse> getAllRegions(Pageable pageable);

}
