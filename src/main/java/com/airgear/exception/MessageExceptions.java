package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class MessageExceptions {

    public MessageExceptions() {
    }

    public static ResponseStatusException messageNotFound(UUID messageId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with id '" + messageId + "' was not found");
    }
}
