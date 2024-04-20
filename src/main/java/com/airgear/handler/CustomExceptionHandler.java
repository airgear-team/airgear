package com.airgear.handler;

import com.airgear.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = MethodNotAllowedException.class)
    public ErrorResponse handleMethodNotAllowedExceptionException(MethodNotAllowedException ex, HttpServletRequest request) {
        return new ErrorResponse(new Date(), HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String errorMessage = String.join("; ", errorMessages);
        return new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorMessage, request.getRequestURI());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return new ErrorResponse(new Date(), ex.getStatus().value(), ex.getMessage(), request.getRequestURI());
    }
}
