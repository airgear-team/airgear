package com.airgear.model.location.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record SaveLocationRequest(

        @NotNull(message = "the settlement must not be null")
        String settlement,

        @NotBlank(message = "the region id must not be blank")
        @PositiveOrZero(message = "the region id must be positive or zero")
        Long region_id

) {
}
