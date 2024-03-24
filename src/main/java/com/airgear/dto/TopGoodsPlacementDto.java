package com.airgear.dto;

import com.airgear.mapper.GoodsMapperImpl;
import com.airgear.model.goods.TopGoodsPlacement;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class TopGoodsPlacementDto {

    //TODO with mapstruct
    private Long id;
    private Long userId;
    private GoodsDto goods;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;

    public TopGoodsPlacement toModel() {
        return TopGoodsPlacement.builder()
                .id(id)
                .userId(userId)
                .goods(goods == null ? null : new GoodsMapperImpl().toModel(goods))
                .startAt(startAt)
                .endAt(endAt)
                .build();
    }

    public static Set<TopGoodsPlacement> toModels(Set<TopGoodsPlacementDto> roles) {
        Set<TopGoodsPlacement> result = new HashSet<>();
        roles.forEach(topGoodsPlacementDto -> result.add(topGoodsPlacementDto.toModel()));
        return result;
    }

    public static TopGoodsPlacementDto toDto(TopGoodsPlacement topGoodsPlacement) {
        return TopGoodsPlacementDto.builder()
                .id(topGoodsPlacement.getId())
                .userId(topGoodsPlacement.getUserId())
                .goods(new GoodsMapperImpl().toDto(topGoodsPlacement.getGoods()))
                .startAt(topGoodsPlacement.getStartAt())
                .endAt(topGoodsPlacement.getEndAt())
                .build();
    }

    public static Set<TopGoodsPlacementDto> toDtoSet(Set<TopGoodsPlacement> roles) {
        Set<TopGoodsPlacementDto> result = new HashSet<>();
        roles.forEach(topGoodsPlacement -> result.add(TopGoodsPlacementDto.toDto(topGoodsPlacement)));
        return result;
    }
}
