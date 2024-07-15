package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GoodsExceptions {

    public static ResponseStatusException goodsNotFound(long goodsId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goods with id '" + goodsId + "' was not found");
    }

    public static ResponseStatusException goodsLimitExceeded(long categoryId) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Goods limit exceeded for category with id '" + categoryId + "'");
    }

    public static ResponseStatusException currencyIsNull(String currencyName) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, currencyName + " currency cannot be null!");
    }

}