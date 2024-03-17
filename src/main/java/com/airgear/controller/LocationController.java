package com.airgear.controller;

import com.airgear.model.goods.Location;
import com.airgear.model.goods.Region;
import com.airgear.service.impl.GoodsServiceImpl;
import com.airgear.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    //TODO
    // 1. Винести залежності в конструктор
    // 2. Винести зайвк логіку в сервіс (в контролері ми повинні викликати тільки один метод сервісу)
    // 3. Створити власні виключення й кидати їх

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private GoodsServiceImpl goodsService;

    @GetMapping("/regions")
    public List<Region> getAllRegions() {
        return locationService.getAllRegions();
    }

    @PostMapping("/create")
    public Location createLocation(@RequestParam String settlement, @RequestParam Long region_id) {
        Region region = locationService.getRegionById(region_id);
        if (region != null) {
            Location location = new Location();
            location.setSettlement(settlement);
            location.setRegionId(region_id);
            return locationService.addLocation(location);
        }
        throw new RuntimeException("don't find region");
    }

}
