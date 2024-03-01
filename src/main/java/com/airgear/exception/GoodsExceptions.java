package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GoodsExceptions {

    public GoodsExceptions() {
    }

    public static ResponseStatusException goodsNotFound(long goodsId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goods with id '" + goodsId + "' was not found");
    }
}
