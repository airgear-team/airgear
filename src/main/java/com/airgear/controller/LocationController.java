package com.airgear.controller;

import com.airgear.model.location.request.SaveLocationRequest;
import com.airgear.model.location.response.LocationResponse;
import com.airgear.model.region.response.RegionResponse;
import com.airgear.service.LocationService;
import com.airgear.utils.Routes;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(Routes.LOCATION)
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid SaveLocationRequest request,
                                                           UriComponentsBuilder ucb) {
        LocationResponse response = locationService.addLocation(request);
        return ResponseEntity
                .created(ucb.path("/{id}").build(response.id()))
                .body(response);
    }

    @GetMapping(
            value = "/regions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<RegionResponse> getAllRegions(@Parameter(hidden = true) Pageable pageable) {
        return locationService.getAllRegions(pageable);
    }
}
