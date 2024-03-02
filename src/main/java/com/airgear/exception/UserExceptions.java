package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for creating custom exceptions related to users.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class UserExceptions {

    public UserExceptions() {
    }

    /**
     * Throws a ResponseStatusException for a not found user.
     *
     * @param userId The ID of the user that was not found.
     * @return ResponseStatusException with a NOT_FOUND status and a descriptive message.
     */
    public static ResponseStatusException userNotFound(long userId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + userId + "' was not found");
    }
}
