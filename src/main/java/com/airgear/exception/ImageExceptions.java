package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ImageExceptions {

    public static ResponseStatusException uploadImageProblem(String imageName) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to upload photo: " + imageName);
    }

    public static ResponseStatusException invalidImageExtension(String imageName) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid image extension: only JPEG and PNG are allowed. File name :" + imageName);
    }

    public static ResponseStatusException invalidImageSize(String imageName) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid file size: must not exceed 10 MB and must not be empty. File name :" + imageName);
    }

}