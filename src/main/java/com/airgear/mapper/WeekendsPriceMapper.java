package com.airgear.mapper;

import com.airgear.dto.WeekendsPriceDto;
import com.airgear.model.goods.WeekendsPrice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekendsPriceMapper {

    WeekendsPriceDto toDto(WeekendsPrice price);

    WeekendsPrice toModel(WeekendsPriceDto dto);
}
