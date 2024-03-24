package com.airgear.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GenerateRentalAgreementException extends RuntimeException{

    public GenerateRentalAgreementException(String message) {
        super(message);
    }
}
