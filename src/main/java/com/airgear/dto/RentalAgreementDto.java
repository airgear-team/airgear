package com.airgear.dto;

import com.airgear.model.RentalAgreement;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public RentalAgreement toRentalAgreement() {
        return RentalAgreement.builder()
                .lessor(lessor)
                .renter(renter)
                .firstdate(firstDate)
                .lastdate(lastDate)
                .goods(goods.toGoods())
                .goal(goal)
                .rentalprice(rentalPrice)
                .lessordocument(lessorDocument)
                .renterdocument(renterDocument)
                .lessorphone(lessorPhone)
                .renterphone(renterPhone)
                .build();
    }

    public static List<RentalAgreement> toRentalAgreements(List<RentalAgreementDto> rentalAgreements) {
        List<RentalAgreement> result = new ArrayList<>();
        rentalAgreements.forEach(rentalAgreement -> result.add(rentalAgreement.toRentalAgreement()));
        return result;
    }

    public static RentalAgreementDto fromRentalAgreement(RentalAgreement rentalAgreement) {
        return RentalAgreementDto.builder()
                .lessor(rentalAgreement.getLessor())
                .renter(rentalAgreement.getRenter())
                .firstDate(rentalAgreement.getFirstdate())
                .lastDate(rentalAgreement.getLastdate())
                .goods(GoodsDto.fromGoods(rentalAgreement.getGoods()))
                .goal(rentalAgreement.getGoal())
                .rentalPrice(rentalAgreement.getRentalprice())
                .lessorDocument(rentalAgreement.getLessordocument())
                .renterDocument(rentalAgreement.getRenterdocument())
                .lessorPhone(rentalAgreement.getLessorphone())
                .renterPhone(rentalAgreement.getRenterphone())
                .build();
    }

    public static List<RentalAgreementDto> fromRentalAgreements(List<RentalAgreement> rentalAgreements) {
        List<RentalAgreementDto> result = new ArrayList<>();
        rentalAgreements.forEach(rentalAgreement -> result.add(RentalAgreementDto.fromRentalAgreement(rentalAgreement)));
        return result;
    }


}
