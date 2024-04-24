package com.airgear.service;

import com.airgear.dto.*;
import com.airgear.model.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.math.BigDecimal;
import java.util.Set;

public interface GoodsService {

    GoodsCreateRequest getGoodsById(Long id);

    GoodsCreateRequest getGoodsById(String ipAddress, String username, Long goodsId);

    void deleteGoods(Goods goods);

    void deleteGoods(String username, Long goodsId);

    Goods saveGoods(Goods goods);

    Goods updateGoods(Goods goods);

    GoodsCreateRequest updateGoods(String username, Long goodsId, GoodsCreateRequest updatedGoods);

    Set<GoodsCreateRequest> getAllGoodsByUsername(String username);

    List<Goods> getAllGoods();

    Page<Goods> getAllGoods(Pageable pageable);

    Page<GoodsCreateRequest> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsCreateRequest> listGoodsByName(Pageable pageable, String goodsName);

    List<GoodsCreateRequest> getRandomGoods(String categoryName, int quantity);

    Page<GoodsCreateRequest> getSimilarGoods(String categoryName, BigDecimal price);

    GoodsCreateResponse createGoods(String username, GoodsCreateRequest goodsCreateRequest);

    GoodsCreateRequest addToFavorites(String username, Long goodsId);

    List<Goods> getTopGoodsPlacements();

    TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto);

}