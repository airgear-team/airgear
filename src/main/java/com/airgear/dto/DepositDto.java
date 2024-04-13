package com.airgear.dto;

import com.airgear.model.goods.enums.Currency;
import com.airgear.model.goods.enums.PriceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DepositDto {
    private BigDecimal depositAmount;
    private Currency depositCurrency;
    private PriceType depositPriceType;
}
