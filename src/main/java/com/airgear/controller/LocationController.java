package com.airgear.controller;

import com.airgear.dto.LocationDto;
import com.airgear.dto.RegionDto;
import com.airgear.model.goods.Region;
import com.airgear.service.impl.GoodsServiceImpl;
import com.airgear.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationServiceImpl locationService;

    @Autowired
    public LocationController(LocationServiceImpl locationService, GoodsServiceImpl goodsService) {
        this.locationService = locationService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/regions")
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        return ResponseEntity.ok(locationService.getAllRegions());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody com.airgear.dto.LocationDto locationDto) {
        return ResponseEntity.ok(locationService.addLocation(locationDto));
    }
}
