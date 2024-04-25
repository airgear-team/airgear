package com.airgear.controller;

import com.airgear.dto.SaveLocationRequestDTO;
import com.airgear.dto.LocationResponseDTO;
import com.airgear.dto.RegionResponseDTO;
import com.airgear.service.LocationService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody @Valid SaveLocationRequestDTO request,
                                                              UriComponentsBuilder ucb) {
        LocationResponseDTO response = locationService.addLocation(request);
        return ResponseEntity
                .created(ucb.path("/{id}").build(response.getId()))
                .body(response);
    }

    @GetMapping(value = "/regions")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<RegionResponseDTO> getAllRegions(@Parameter(hidden = true) Pageable pageable) {
        return locationService.getAllRegions(pageable);
    }
}
