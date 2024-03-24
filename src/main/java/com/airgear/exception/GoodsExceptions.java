package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for creating custom exceptions related to goods.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class GoodsExceptions {

    public GoodsExceptions() {
    }

    /**
     * Throws a ResponseStatusException for not found goods.
     *
     * @param goodsId The ID of the goods that were not found.
     * @return ResponseStatusException with a NOT_FOUND status and a descriptive message.
     */
    public static ResponseStatusException goodsNotFound(long goodsId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goods with id '" + goodsId + "' was not found");
    }

    /**
     * Throws a ResponseStatusException for exceeding the goods limit in a category.
     *
     * @param categoryId The ID of the category where the product limit was exceeded.
     * @return ResponseStatusException with a BAD_REQUEST status and a descriptive message.
     */
    public static ResponseStatusException goodsLimitExceeded(long categoryId) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Goods limit exceeded for category with id '" + categoryId + "'");
    }
}
