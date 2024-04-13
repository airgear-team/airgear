package com.airgear.mapper;

import com.airgear.dto.RentalCardDto;
import com.airgear.model.RentalCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalCardMapper {

    @Mapping(target = "lessor.email", source = "lessorUsername")
    @Mapping(target = "renter.email", source = "renterUsername")
    @Mapping(target = "goods.id", source = "goodsId")
    RentalCard toModel(RentalCardDto rentalCardDto);
}