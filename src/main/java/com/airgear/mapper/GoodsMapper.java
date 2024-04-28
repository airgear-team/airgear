package com.airgear.mapper;

import com.airgear.dto.*;
import com.airgear.model.Goods;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, LocationMapper.class, CategoryMapper.class , DepositMapper.class})
public interface GoodsMapper {

    Goods toModel(GoodsCreateRequest dto);

    GoodsGetResponse toGetResponse(Goods goods);
    GoodsUpdateResponse toUpdateResponse(Goods goods);
    GoodsCreateResponse toCreateResponse(Goods goods);
    Goods toModel(GoodsGetResponse dto);
    Goods toModel(GoodsUpdateRequest dto);

    Set<GoodsSearchResponse> toSearchResponse(Set<Goods> goods);
    List<GoodsGetRandomResponse> toGetRandomResponseList(List<Goods> goods);
    List<GoodsGetResponse> toGetResponseList(List<Goods> goods);
    GoodsSearchResponse toFilterResponse(Goods goods);
}
