package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreementRequest {
    private String lessor;
    private String renter;
    private OffsetDateTime firstDate;
    private OffsetDateTime lastDate;
    private GoodsGetResponse goods;
    private String goal;
    private Double rentalPrice;
    private String lessorDocument;
    private String renterDocument;
    private String lessorPhone;
    private String renterPhone;
}
