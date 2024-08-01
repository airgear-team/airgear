package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ImageExceptions {

    public static ResponseStatusException uploadImageProblem(String imageName) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload photo: " + imageName);
    }

    public static ResponseStatusException invalidImageExtension(String imageName) {
        throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Invalid image extension: only JPEG and PNG are allowed. File name :" + imageName);
    }

    public static ResponseStatusException invalidImageSize(String imageName) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid file size: must not exceed 10 MB and must not be empty. File name :" + imageName);
    }

    public static ResponseStatusException imageNotFound(long userId, long goodsId, String imageName) {
        throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                "Image not found. " + "UserId : " + userId + ". GoodsId : " + goodsId + ". File name : " + imageName);
    }

    public static ResponseStatusException tooManyImages(int maxImages) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many images. The maximum allowed is " + maxImages);
    }

    public static ResponseStatusException urlNotExistInGoods(long goodsId, String imageUrl) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The goods with id '" + goodsId + "' does not contain image with url '" + imageUrl + "'.");
    }
}
