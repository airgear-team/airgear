package com.airgear.service.impl;

import com.airgear.service.LocationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service(value = "locationService")
public class LocationServiceImpl implements LocationService {

    private final Dotenv dotenv;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public LocationServiceImpl(Dotenv dotenv, RestTemplate restTemplate) {
        this.dotenv = dotenv;
        this.apiKey = dotenv.get("API_KEY");
        this.restTemplate = restTemplate;
        this.baseUrl = "https://api.delengine.com/v1.0";
    }

    @Override
    public HashMap<String, String> getAllRegions() throws Exception {
        String url = baseUrl + "/regions" + "?token=" + apiKey;
        // HashMap stores region uuid and region name
        HashMap<String, String> regions = new HashMap<String, String>();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode dataNode = root.get("data");
            for (JsonNode node: dataNode) {
                String regionName = node.get("name_uk").asText();
                String regionUuid = node.get("uuid").asText();
                regions.put(regionName, regionUuid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return regions;
    }

    @Override
    public List<String> getSettlementsOfRegion(String region) {
        return null;
    }
}
