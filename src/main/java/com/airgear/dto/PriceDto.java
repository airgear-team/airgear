package com.airgear.dto;

import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceDto {
    private BigDecimal priceAmount;
    private Currency priceCurrency;
    private PriceType priceType;
}
