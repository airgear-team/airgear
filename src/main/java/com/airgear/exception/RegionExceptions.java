package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for creating custom exceptions related to region.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ResponseStatusException
 */
public class RegionExceptions {

    public RegionExceptions() {
    }

    /**
     * Throws a ResponseStatusException for a not found region.
     *
     * @param regionId the ID of the region that was not found.
     * @return ResponseStatusException with a NOT_FOUND status and a descriptive region.
     */
    public static ResponseStatusException regionNotFound(long regionId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Region with id '" + regionId + "' was not found");
    }
}
