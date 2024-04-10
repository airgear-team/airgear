package com.airgear.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@NoArgsConstructor
public class CategoryExceptions {

    public static ResponseStatusException categoryNotFound(String name) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with name '" + name + "' was not found");
    }
}
