package com.airgear.exception;

// TODO to use "UserExceptions.duplicateUsername(String username)" or "UserExceptions.duplicateEmail(String email)" or "UserExceptions.duplicatePhone(String phone)"
public class UserUniquenessViolationException extends RuntimeException {
    public UserUniquenessViolationException(String message) {
        super(message);
    }
}
