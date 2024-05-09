package com.airgear.mapper;

import com.airgear.dto.GoodsImagesResponse;
import com.airgear.model.GoodsImages;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodsImagesMapper {
    GoodsImagesResponse toDto(GoodsImages goodsImages);
    List<GoodsImagesResponse> toDtoList(List<GoodsImages> goodsImages);

    GoodsImages toModel(GoodsImagesResponse goodsImagesResponse);
}
