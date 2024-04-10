package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserExceptions {

    public static ResponseStatusException userNotFound(long userId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + userId + "' was not found");
    }

    public static ResponseStatusException userNotFound(String email) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email '" + email + "' was not found");
    }

    public static ResponseStatusException duplicateEmail(String email) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + email + " already taken");
    }

    public static ResponseStatusException duplicatePhone(String phone) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone " + phone + " already taken");
    }

    public static ResponseStatusException AccessDenied(String name) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: " + name);
    }
}
