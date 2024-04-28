package com.airgear.mapper;

import com.airgear.dto.WeekendsPriceRequest;
import com.airgear.dto.WeekendsPriceResponse;
import com.airgear.model.WeekendsPrice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekendsPriceMapper {

    WeekendsPriceResponse toDto(WeekendsPrice price);

    WeekendsPrice toModel(WeekendsPriceRequest dto);
}
