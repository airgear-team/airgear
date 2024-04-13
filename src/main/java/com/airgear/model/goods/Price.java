package com.airgear.model.goods;

import com.airgear.model.goods.enums.Currency;
import com.airgear.model.goods.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @NotNull(message = "priceAmount cannot be null")
    private BigDecimal priceAmount;
    @NotNull(message = "priceCurrency cannot be null")
    private Currency priceCurrency;
    @NotNull(message = "priceType cannot be null")
    private PriceType priceType;
}
