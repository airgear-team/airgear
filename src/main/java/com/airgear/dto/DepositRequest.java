package com.airgear.dto;

import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private BigDecimal depositAmount;
    private Currency depositCurrency;
    private PriceType depositPriceType;
}