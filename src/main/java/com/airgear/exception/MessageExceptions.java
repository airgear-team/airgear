package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Utility class for creating custom exceptions related to messages.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class MessageExceptions {

    public MessageExceptions() {
    }

    /**
     * Creates a ResponseStatusException for a not found message.
     *
     * @param messageId The ID of the message that was not found.
     * @return ResponseStatusException with a NOT_FOUND status and a descriptive message.
     */
    public static ResponseStatusException messageNotFound(UUID messageId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with id '" + messageId + "' was not found");
    }
}
