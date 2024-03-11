package com.airgear.exception;

public class UserUniquenessViolationException extends RuntimeException {
    public UserUniquenessViolationException(String message) {
        super(message);
    }
}
