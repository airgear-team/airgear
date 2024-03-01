package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserExceptions {

    public UserExceptions() {
    }

    public static ResponseStatusException userNotFound(long userId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + userId + "' was not found");
    }
}
