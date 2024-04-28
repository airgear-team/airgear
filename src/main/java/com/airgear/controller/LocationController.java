package com.airgear.controller;

import com.airgear.dto.LocationResponse;
import com.airgear.dto.LocationSaveRequest;
import com.airgear.dto.RegionResponse;
import com.airgear.service.LocationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationSaveRequest request,
                                                           UriComponentsBuilder ucb) {
        LocationResponse response = locationService.addLocation(request);
        return ResponseEntity
                .created(ucb.path("/location/{id}").build(response.getLocationId()))
                .body(response);
    }

    @GetMapping(value = "/regions")
    @PageableAsQueryParam
    public Page<RegionResponse> getAllRegions(@Parameter(hidden = true) Pageable pageable) {
        return locationService.getAllRegions(pageable);
    }
}
