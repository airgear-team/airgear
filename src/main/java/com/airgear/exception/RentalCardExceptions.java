package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RentalCardExceptions {

    public RentalCardExceptions() {
    }

    public static ResponseStatusException rentalCardNotFound(long id) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental card with id '" + id + "' was not found");
    }
}
