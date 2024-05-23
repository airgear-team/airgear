package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryExceptions {

    public static ResponseStatusException categoryNotFound(String name) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with name '" + name + "' was not found");
    }
    public static ResponseStatusException categoryNotFound(Integer id) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with id '" + id + "' was not found");
    }

}