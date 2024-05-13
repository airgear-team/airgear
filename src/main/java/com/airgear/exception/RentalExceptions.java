package com.airgear.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RentalExceptions {

    public static ResponseStatusException badPeriod() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Don't fill duration or last day in rental card!");
    }

    public static ResponseStatusException badFile() {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The problem with loading the lease agreement.");
    }

}