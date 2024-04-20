package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationException {

    public static ResponseStatusException locationNotFound(Integer locationId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id " + locationId + " not found");
    }

}