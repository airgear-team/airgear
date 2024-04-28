package com.airgear.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class LocationSaveRequest {
        @NotNull(message = "the settlement must not be null")
        @Setter(AccessLevel.NONE)
        private String settlement;

        @NotBlank(message = "the region id must not be blank")
        @PositiveOrZero(message = "the region id must be positive or zero")
        @Setter(AccessLevel.NONE)
        private Long regionId;
}

