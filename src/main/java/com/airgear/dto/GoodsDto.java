package com.airgear.dto;

import com.airgear.model.goods.Goods;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

/**
 * GoodsDto class. Fields are similar to Goods entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class GoodsDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal weekendsPrice;
    private LocationDto location;
    private CategoryDto category;
    private String phoneNumber;

    public Goods toGoods() {
        return Goods.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .weekendsPrice(weekendsPrice)
                .location(location.toLocation())
                .category(category.toCategory())
                .phoneNumber(phoneNumber)
                .build();
    }

    public static List<Goods> toGoodsList(List<GoodsDto> goods) {
        List<Goods> result = new ArrayList<>();
        goods.forEach(goodsDto -> result.add(goodsDto.toGoods()));
        return result;
    }

    public static Set<Goods> toGoodsSet(Set<GoodsDto> goods) {
        Set<Goods> result = new HashSet<>();
        goods.forEach(goodsDto -> result.add(goodsDto.toGoods()));
        return result;
    }

    public static GoodsDto fromGoods(Goods goods) {
        return GoodsDto.builder()
                .id(goods.getId())
                .name(goods.getName())
                .description(goods.getDescription())
                .price(goods.getPrice())
                .weekendsPrice(goods.getWeekendsPrice())
                .location(LocationDto.fromLocation(goods.getLocation()))
                .category(CategoryDto.fromCategory(goods.getCategory()))
                .phoneNumber(goods.getPhoneNumber())
                .build();
    }

    public static List<GoodsDto> fromGoodsList(List<Goods> goodsList) {
        List<GoodsDto> result = new ArrayList<>();
        goodsList.forEach(goods -> result.add(GoodsDto.fromGoods(goods)));
        return result;
    }

    public static Set<GoodsDto> fromGoodsSet(Set<Goods> goodsList) {
        Set<GoodsDto> result = new HashSet<>();
        goodsList.forEach(goods -> result.add(GoodsDto.fromGoods(goods)));
        return result;
    }
}