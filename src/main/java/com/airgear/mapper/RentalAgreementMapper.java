package com.airgear.mapper;

import com.airgear.dto.RentalAgreementRequest;
import com.airgear.entity.RentalAgreement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalAgreementMapper {
    RentalAgreementRequest toDto(RentalAgreement rentalAgreement);

    RentalAgreement toModel(RentalAgreementRequest dto);
}
