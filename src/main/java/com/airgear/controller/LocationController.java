package com.airgear.controller;

import com.airgear.model.goods.Goods;
import com.airgear.model.goods.Location;
import com.airgear.model.goods.Regions;
import com.airgear.service.impl.GoodsServiceImpl;
import com.airgear.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private GoodsServiceImpl goodsService;

    @GetMapping("/regions")
    public List<Regions> getAllRegions() {
        return locationService.getAllRegions();
    }

    @PostMapping("/create")
    public void createLocation(@RequestParam String settlement, @RequestParam Long region_id) {
        Regions region = locationService.getRegionById(region_id);
        if (region != null) {
            Location location = new Location();
            location.setSettlement(settlement);
            location.setRegionId(region_id);
            locationService.addLocation(location);
        }
    }

}
