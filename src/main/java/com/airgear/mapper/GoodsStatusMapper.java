package com.airgear.mapper;

import com.airgear.dto.GoodsStatusDto;
import com.airgear.model.goods.GoodsStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodsStatusMapper {
    GoodsStatusDto toDto(GoodsStatus goodsStatus);

    GoodsStatus toModel(GoodsStatusDto dto);
}
