package com.airgear.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name length must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @NotBlank(message = "Location cannot be blank")
    @Size(min = 3, max = 255, message = "Location length must be between 3 and 255 characters")
    private String location;

    @Embedded
    private Deposit deposit;

    @Embeddable
    class Deposit {
        private BigDecimal amount;
        private Currency currency;

        enum Currency {UAH, EUR, USD}
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}