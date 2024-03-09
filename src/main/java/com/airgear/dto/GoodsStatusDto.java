package com.airgear.dto;

import com.airgear.model.goods.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * GoodsStatusDto class. Fields are similar to GoodsStatus entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class GoodsStatusDto {
    private Long id;
    private String name;

    public GoodsStatus toGoodsStatus() {
        return GoodsStatus.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static List<GoodsStatus> toGoodsStatuses(List<GoodsStatusDto> goodsStatuses) {
        List<GoodsStatus> result = new ArrayList<>();
        goodsStatuses.forEach(goodsStatus -> result.add(goodsStatus.toGoodsStatus()));
        return result;
    }

    public static GoodsStatusDto fromGoodsStatus(GoodsStatus goodsStatus) {
        return GoodsStatusDto.builder()
                .id(goodsStatus.getId())
                .name(goodsStatus.getName())
                .build();
    }

    public static List<GoodsStatusDto> fromGoodsStatuses(List<GoodsStatus> goodsStatuses) {
        List<GoodsStatusDto> result = new ArrayList<>();
        goodsStatuses.forEach(goodsStatus -> result.add(GoodsStatusDto.fromGoodsStatus(goodsStatus)));
        return result;
    }


}
