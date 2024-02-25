package com.airgear.service;

import java.util.HashMap;
import java.util.List;

public interface LocationService {

    HashMap<String, String> getAllRegions() throws Exception;
    List<String> getSettlementsOfRegion(String region) throws Exception;


}
