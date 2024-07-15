package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailExceptions {
    public static ResponseStatusException unableToSendEmail(String address) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                "An error occurred while sending the email to recipient " + address);
    }

    public static ResponseStatusException unableToSaveEmail(String address) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                "An error occurred while saving the email to recipient " + address);
    }

    public static ResponseStatusException notFoundEmails() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not found such emails");
    }

    public static ResponseStatusException pageNotFound() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
    }
}
