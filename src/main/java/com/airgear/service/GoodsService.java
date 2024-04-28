package com.airgear.service;

import com.airgear.dto.*;
import com.airgear.model.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface GoodsService {

    GoodsGetResponse getGoodsById(Long id);

    GoodsGetResponse getGoodsById(String ipAddress, String username, Long goodsId);

    void deleteGoods(Goods goods);

    void deleteGoods(String username, Long goodsId);

    Goods updateGoods(Goods goods);

    GoodsUpdateResponse updateGoods(String username, Long goodsId, GoodsUpdateRequest updatedGoods);

    Set<GoodsSearchResponse> getAllGoodsByUsername(String username);

    Page<GoodsGetResponse> getAllGoods(Pageable pageable);

    Page<GoodsSearchResponse> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsSearchResponse> listGoodsByName(Pageable pageable, String goodsName);

    List<GoodsGetRandomResponse> getRandomGoods(String categoryName, int quantity);

    Page<GoodsSearchResponse> getSimilarGoods(String categoryName, BigDecimal price);

    GoodsCreateResponse createGoods(String email, GoodsCreateRequest goodsDto);

    GoodsGetResponse addToFavorites(String username, Long goodsId);

    List<Goods> getTopGoodsPlacements();

    TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto);

}
