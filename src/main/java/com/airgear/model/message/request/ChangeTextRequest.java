package com.airgear.model.message.request;

import javax.validation.constraints.NotBlank;

public record ChangeTextRequest(

        @NotBlank(message = "text must not be empty")
        String text

) {
}
