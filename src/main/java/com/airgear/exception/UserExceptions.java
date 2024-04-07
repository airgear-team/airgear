package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserExceptions {

    public static ResponseStatusException userNotFound(long userId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + userId + "' was not found");
    }

    public static ResponseStatusException userIsBlocked(String userName) {
        throw new ResponseStatusException(HttpStatus.LOCKED, "User with name '" + userName + "' is blocked");
    }

    public static ResponseStatusException userNotFound(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username '" + username + "' was not found");
    }

    public static ResponseStatusException userNotFoundAuthorized(String username) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with username '" + username + "' was not found");
    }

    public static ResponseStatusException userUniqueness(String name, String value) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with "+name+" '" + value + "' already exists");
    }

    public static ResponseStatusException userDataNotFound(String name, String value) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, name+": " + value + "' was not found");
    }

    public static ResponseStatusException AccessDenied(String name) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: " + name);
    }
}
