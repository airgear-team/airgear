package com.airgear.mapper;

import com.airgear.dto.GoodsDto;
import com.airgear.model.goods.Goods;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = GoodsMapper.class)
public interface GoodsSetMapper {
    Set<GoodsDto> toDtoSet(Set<Goods> goods);

    Set<Goods> toGoodsSet(Set<GoodsDto> dtos);
}
