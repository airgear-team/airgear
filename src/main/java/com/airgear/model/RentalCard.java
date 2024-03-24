package com.airgear.model;

import com.airgear.model.goods.Goods;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RentalCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "lessor_id", nullable = false)
    private User lessor;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @Column(name = "first_date")
    @NotNull(message = "First date cannot be null")
    private OffsetDateTime firstDate;

    @Column(name = "last_date")
    @NotNull(message = "Last date cannot be null")
    private OffsetDateTime lastDate;

    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ChronoUnit duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Long quantity;


    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    @Column(name = "rental_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal rentalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal fine;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @Column(name = "deleted_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime deletedAt;

}