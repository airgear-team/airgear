package com.airgear.mapper;

import com.airgear.dto.RentalCardResponse;
import com.airgear.model.RentalCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalCardMapper {

    @Mapping(target = "lessor", source = "lessor.email")
    @Mapping(target = "renter", source = "renter.email")
    @Mapping(target = "goods", source = "goods.name")
    @Mapping(target = "price", source = "rentalPrice")
    RentalCardResponse toDto(RentalCard rentalCard);

}
