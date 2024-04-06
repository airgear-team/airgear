package com.airgear.service;

import com.airgear.dto.SaveLocationRequestDTO;
import com.airgear.dto.LocationResponseDTO;
import com.airgear.dto.RegionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    LocationResponseDTO addLocation(SaveLocationRequestDTO request);

    Page<RegionResponseDTO> getAllRegions(Pageable pageable);

}
