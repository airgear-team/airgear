package com.airgear.mapper;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.dto.GoodsCreateResponse;
import com.airgear.model.Goods;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, LocationMapper.class, CategoryMapper.class , DepositMapper.class})
public interface GoodsMapper {

    GoodsCreateRequest toDtoRequest(Goods goods);

    Goods toModelRequest(GoodsCreateRequest dto);

    GoodsCreateResponse toDtoResponse(Goods goods);

    Goods toModelResponse(GoodsCreateResponse dto);

    Set<GoodsCreateRequest> toDtoSet(Set<Goods> goods);

    Set<Goods> toModelSet(Set<GoodsCreateRequest> dtos);

    List<GoodsCreateRequest> toDtoList(List<Goods> goods);
}
