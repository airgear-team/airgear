package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class RentalCardResponse {

    private Long id;

    private String lessor;

    private String renter;

    private String goods;

    private OffsetDateTime firstDate;

    private OffsetDateTime lastDate;

    private ChronoUnit duration;

    private Long quantity;

    private BigDecimal price;

    private BigDecimal fine;

    private String description;

}
