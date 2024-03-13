package com.airgear.mapper;

import com.airgear.dto.GoodsDto;
import com.airgear.model.goods.Goods;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, LocationMapper.class, CategoryMapper.class})
public interface GoodsMapper {
    GoodsDto toDto(Goods goods);

    Goods toGoods(GoodsDto dto);
}
