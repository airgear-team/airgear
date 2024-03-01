package com.airgear.model.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record SaveMessageRequest(

        @NotBlank(message = "text must not be empty")
        String text,

        @NotNull(message = "goods id must not be null")
        long goodsId,

        @NotNull(message = "user id must not be null")
        long userId

) {
}
