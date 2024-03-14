package com.airgear.mapper;

import com.airgear.dto.RentalAgreementDto;
import com.airgear.model.RentalAgreement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalAgreementMapper {
    RentalAgreementDto toDto(RentalAgreement rentalAgreement);

    RentalAgreement toModel(RentalAgreementDto dto);
}
