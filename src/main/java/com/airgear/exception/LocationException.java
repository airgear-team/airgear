package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationException {

    public static ResponseStatusException locationNotFound(Integer locationId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id '" + locationId + "' not found");
    }

    public static ResponseStatusException invalidLocationName(String name) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The location name '" + name + "' must consist of at least 3 characters.");
    }
}
