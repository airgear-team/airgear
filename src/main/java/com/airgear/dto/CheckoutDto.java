package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CheckoutDto {
    private String action;
    private BigDecimal amount;
    private String currency;
    private String description;
    private UUID order_id;
    private Integer version;
}