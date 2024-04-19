package com.airgear.handler;

import com.airgear.entity.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = MethodNotAllowedException.class)
    public ErrorResponse handleMethodNotAllowedExceptionException(MethodNotAllowedException ex, HttpServletRequest request) {
        return new ErrorResponse(new Date(), HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidFormatException.class)
    public ErrorResponse invalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
        return new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> accessDeniedException() {
        return ResponseEntity.status(HttpStatus.LOCKED).body("Access denied");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Illegal argument");
    }

}