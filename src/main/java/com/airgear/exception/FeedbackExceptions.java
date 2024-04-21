package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeedbackExceptions {

    public FeedbackExceptions() {
    }

    public static ResponseStatusException feedbackNotFound(long id) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback with id '" + id + "' was not found");
    }
}
