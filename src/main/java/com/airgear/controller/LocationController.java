package com.airgear.controller;

import com.airgear.dto.LocationResponse;
import com.airgear.dto.RegionResponse;
import com.airgear.service.LocationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping(value = "/regions")
    @PageableAsQueryParam
    public Page<RegionResponse> getAllRegions(@Parameter(hidden = true) Pageable pageable) {
        return locationService.getAllRegions(pageable);
    }

    @GetMapping(value = "/{name}")
    @PageableAsQueryParam
    public Page<LocationResponse> getAllLocationsByName(@Parameter(hidden = true) Pageable pageable,
                                                        @PathVariable String name) {
        return locationService.getAllLocationsByName(pageable, name);
    }
}
