package com.airgear.controller;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Regions;
import com.airgear.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService;

    @GetMapping("/regions")
    public List<Regions> getAllRegions() {
        return locationService.getAllRegions();
    }

    @PostMapping("/create")
    public void createLocation(@RequestParam Long region_id, @RequestParam String settlement) {
        Regions region = locationService.getRegionById(region_id);
        if (region != null) {
            Location location = new Location();
            location.setRegionId(region_id);
            location.setSettlement(settlement);
            locationService.addLocation(location);
        }
    }

}
