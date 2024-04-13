package com.airgear.model.goods;

import com.airgear.model.goods.enums.Currency;
import com.airgear.model.goods.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekendsPrice {

    private BigDecimal weekendsPriceAmount;
    private Currency weekendsPriceCurrency;
    private PriceType weekendsPriceType;
}
