package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class RentalCardDto {
        private Long id;
        private String lessorUsername;
        private String renterUsername;
        private OffsetDateTime firstDate;
        private OffsetDateTime lastDate;
        private ChronoUnit duration;
        private Long quantity;
        private Long goodsId;
        private BigDecimal rentalPrice;
        private BigDecimal fine;
        private String description;
}