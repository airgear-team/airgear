package com.airgear.model.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Represents a request object for saving a new message with specific fields.
 * This class is designed as a record, providing immutability and a concise syntax.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveMessageRequest(

        @NotBlank(message = "text must not be empty")
        String text,

        @NotNull(message = "goods id must not be null")
        long goodsId,

        @NotNull(message = "user id must not be null")
        long userId

) {
}
