package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalCardSaveRequest {

    @PositiveOrZero(message = "lessor id is mandatory")
    private long lessorId;

    @PositiveOrZero(message = "renter id is mandatory")
    private long renterId;

    @PositiveOrZero(message = "goods id is mandatory")
    private long goodsId;

    @NotNull(message = "first date must not be null")
    private OffsetDateTime firstDate;

    @NotNull(message = "last date must not be null")
    private OffsetDateTime lastDate;

    @NotNull(message = "duration must not be null")
    private ChronoUnit duration;

    @PositiveOrZero(message = "quantity is mandatory")
    private Long quantity;

    @Positive(message = "price must be greater then 0.0")
    private BigDecimal price;

    private BigDecimal fine;

    @NotBlank(message = "description of goods is mandatory")
    @Size(min = 10, max = 1000, message = "description length must be between 10 and 1000 characters")
    private String description;

}
