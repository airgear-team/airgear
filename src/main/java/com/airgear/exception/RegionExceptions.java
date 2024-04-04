package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegionExceptions {

    public static ResponseStatusException regionNotFound(long regionId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Region with id '" + regionId + "' was not found");
    }
}
