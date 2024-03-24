package com.airgear.mapper;

import com.airgear.dto.GoodsDto;
import com.airgear.model.goods.Goods;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, LocationMapper.class, CategoryMapper.class})
public interface GoodsMapper {
    GoodsDto toDto(Goods goods);

    Goods toModel(GoodsDto dto);

    Set<GoodsDto> toDtoSet(Set<Goods> goods);

    Set<Goods> toModelSet(Set<GoodsDto> dtos);

    List<GoodsDto> toDtoList(List<Goods> goods);
}
