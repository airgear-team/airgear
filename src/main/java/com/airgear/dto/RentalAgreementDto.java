package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * RentalAgreementDto class. Fields are similar to RentalAgreement entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class RentalAgreementDto {
    private String lessor;
    private String renter;
    private OffsetDateTime firstDate;
    private OffsetDateTime lastDate;
    private GoodsDto goods;
    private String goal;
    private double rentalPrice;
    private String lessorDocument;
    private String renterDocument;
    private String lessorPhone;
    private String renterPhone;
}
