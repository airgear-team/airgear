package com.airgear.dto;

import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {
    private BigDecimal priceAmount;

    @NotNull(message = "Price currency cannot be null!")
    private Currency priceCurrency;

    private PriceType priceType;
}
