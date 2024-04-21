package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalCardChangeDurationRequest {

    @NotNull(message = "first date must not be null")
    private OffsetDateTime firstDate;

    @NotNull(message = "last date must not be null")
    private OffsetDateTime lastDate;

    @NotNull(message = "duration must not be null")
    private ChronoUnit duration;

}
