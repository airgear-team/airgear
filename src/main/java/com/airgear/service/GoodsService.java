package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.TopGoodsPlacementDto;
import com.airgear.model.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface GoodsService {

    GoodsDto getGoodsById(Long id);

    GoodsDto getGoodsById(String ipAddress, String username, Long goodsId);

    void deleteGoods(Goods goods);

    void deleteGoods(String username, Long goodsId);

    Goods updateGoods(Goods goods);

    GoodsDto updateGoods(String username, Long goodsId, GoodsDto updatedGoods);

    Set<GoodsDto> getAllGoodsByUsername(String username);

    List<Goods> getAllGoods();

    Page<Goods> getAllGoods(Pageable pageable);

    Page<GoodsDto> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsDto> listGoodsByName(Pageable pageable, String goodsName);

    List<GoodsDto> getRandomGoods(String categoryName, int quantity);

    Page<GoodsDto> getSimilarGoods(String categoryName, BigDecimal price);

    GoodsDto createGoods(String email, GoodsDto goodsDto);

    GoodsDto addToFavorites(String username, Long goodsId);

    List<Goods> getTopGoodsPlacements();

    TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto);

}