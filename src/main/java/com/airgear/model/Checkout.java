package com.airgear.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * This model implements the entity that is required
 * to create a correct request to LiqPay system to get
 * LiqPay's payment page
 *
 * For more information, please, read LiqPay's documentation
 * @see <a href="https://www.liqpay.ua/documentation/api/aquiring/checkout/doc">
 *     LiqPay's checkout documentation
 *</a>
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Type of operation.
     * Possible values: pay - payment,
     *                  hold - blocking funds on the sender's account,
     *                  subscribe - regular payment,
     *                  paydonate - donation
     */
    @NotNull(message = "Action cannot be null")
    private Action action;

    @Getter
    @AllArgsConstructor
    public enum Action {
        pay("pay"),
        HOLD("hold"),
        SUBSCRIBE("subscribe"),
        PAYDONATE("paydonate");

        private final String value;
    }

    /**
     * Payment amount
     */
    @NotNull(message = "Ammount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @Getter
    @AllArgsConstructor
    public enum Currency {
        UAH("UAH"),
        EUR("EUR"),
        USD("USD");

        private final String value;
    }

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Version cannot be null")
    private int version;

}
