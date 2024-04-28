package com.airgear.dto;

import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WeekendsPriceResponse {
    private BigDecimal weekendsPriceAmount;
    private Currency weekendsPriceCurrency;
    private PriceType weekendsPriceType;
}
