package com.airgear.dto;

import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekendsPriceRequest {
    @DecimalMax(value="100000000.00", message = "Max value = 100000000.00!")
    @DecimalMin(value="0.00", message = "Min value = 0.00!")
    @Digits(integer = 9,fraction=2,message = "Value out of bounds (<9 digits>.<2 digits> expected)!")
    private BigDecimal weekendsPriceAmount;

    private Currency weekendsPriceCurrency;
    private PriceType weekendsPriceType;
}
