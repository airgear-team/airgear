package com.airgear.dto;

import com.airgear.model.Checkout;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CheckoutDTO {

    private String action;
    private BigDecimal amount;
    private String currency;
    private String description;
    private UUID order_id;
    private Integer version;

    public Checkout toModel() {
        return Checkout.builder()
                .id(order_id)
                .action(Checkout.Action.valueOf(action))
                .amount(amount)
                .currency(Checkout.Currency.valueOf(currency))
                .description(description)
                .build();
    }

    public CheckoutDTO toDto(Checkout checkout) {
        return CheckoutDTO.builder()
                .action(checkout.getAction().toString())
                .amount(checkout.getAmount())
                .currency(checkout.getCurrency().toString())
                .description(checkout.getDescription())
                .order_id(checkout.getId())
                .version(checkout.getVersion())
                .build();
    }

    public Map<String, String> toMap() throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            if (field.get(this) != null) {
                map.put(field.getName(), field.get(this).toString());
            }
        }

        return map;
    }
}
