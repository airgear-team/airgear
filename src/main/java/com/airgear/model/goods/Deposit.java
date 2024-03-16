package com.airgear.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    private BigDecimal amount;
    private Currency currency;

    enum Currency {UAH, EUR, USD}
}