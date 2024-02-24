package com.airgear.service;

import java.util.List;

public interface LocationService {

    List<String> getAllRegions() throws Exception;
    List<String> getSettlementsOfRegion(String region) throws Exception;


}
