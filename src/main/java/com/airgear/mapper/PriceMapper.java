package com.airgear.mapper;

import com.airgear.dto.PriceDto;
import com.airgear.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceDto toDto(Price price);

    Price toModel(PriceDto dto);
}
