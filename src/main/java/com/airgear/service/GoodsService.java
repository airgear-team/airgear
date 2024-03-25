package com.airgear.service;

import com.airgear.dto.*;
import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface GoodsService {

    Goods getGoodsById(Long id);

    GoodsDto getGoodsById(String ipAddress, String username, Long goodsId);

    void deleteGoods(Goods goods);

    void deleteGoods(String username, Long goodsId);

    Goods saveGoods(Goods goods);

    Goods updateGoods(Goods goods);

    GoodsDto updateGoods(String username, Long goodsId, GoodsDto updatedGoods);

    Set<GoodsDto> getAllGoodsByUsername(String username);

    List<Goods> getAllGoods();

    Page<Goods> getAllGoods(Pageable pageable);

    Page<Goods> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsDto> listGoodsByName(Pageable pageable, String goodsName);

    int getNewGoodsFromPeriod(OffsetDateTime fromDate, OffsetDateTime toDate);

    CountDeletedGoodsDTO countDeletedGoods(OffsetDateTime startDate, OffsetDateTime endDate, String category);

    Long getTotalNumberOfGoods();

    TotalNumberOfGoodsResponse getTotalNumberOfGoodsResponse();

    AmountOfGoodsByCategoryResponse getAmountOfGoodsByCategory();

    TotalNumberOfTopGoodsResponse getTotalNumberOfTopGoodsResponse();

    List<GoodsDto> getRandomGoods(String categoryName, int quantity);

    Page<GoodsDto> getSimilarGoods(String categoryName, BigDecimal price);

    Map<CategoryDto, Long> getAmountOfNewGoodsByCategory(OffsetDateTime fromDate, OffsetDateTime toDate);

    void saveGoodsView(String ip, Long userId, Goods goods);

    GoodsDto createGoods(String username, GoodsDto goodsDto);

    GoodsDto addToFavorites(String username, Long goodsId);

    List<Goods> getTopGoodsPlacements();

    TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto);

}