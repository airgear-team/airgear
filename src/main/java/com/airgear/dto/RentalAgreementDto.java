package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class RentalAgreementDto {
    private String lessor;
    private String renter;
    private OffsetDateTime firstDate;
    private OffsetDateTime lastDate;
    private GoodsDto goods;
    private String goal;
    private Double rentalPrice;
    private String lessorDocument;
    private String renterDocument;
    private String lessorPhone;
    private String renterPhone;
}
