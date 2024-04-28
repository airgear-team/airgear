package com.airgear.mapper;

import com.airgear.dto.PriceRequest;
import com.airgear.dto.PriceResponse;
import com.airgear.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponse toDto(Price price);

    Price toModel(PriceRequest dto);
}
