package com.airgear.dto;

import com.airgear.model.goods.enums.Currency;
import com.airgear.model.goods.enums.PriceType;
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
