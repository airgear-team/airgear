package com.airgear.mapper;

import com.airgear.dto.TopGoodsPlacementDto;
import com.airgear.model.TopGoodsPlacement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TopGoodsPlacementMapper {
    TopGoodsPlacementDto toDto(TopGoodsPlacement topGoodsPlacement);

    TopGoodsPlacement toModel(TopGoodsPlacementDto topGoodsPlacementDto);

}
