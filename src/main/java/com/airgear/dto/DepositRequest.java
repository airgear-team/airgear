package com.airgear.dto;

import com.airgear.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private BigDecimal depositAmount;
    @NotNull(message = "Deposit currency cannot be null!")
    private Currency depositCurrency;
}
