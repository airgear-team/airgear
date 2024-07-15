package com.airgear.dto;

import com.airgear.model.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DepositResponse {
    private BigDecimal depositAmount;
    private Currency depositCurrency;
}
