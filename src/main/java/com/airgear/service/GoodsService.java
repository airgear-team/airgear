package com.airgear.service;

import com.airgear.dto.AmountOfGoodsByCategoryResponse;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.TotalNumberOfGoodsResponse;
import com.airgear.dto.CountDeletedGoodsDTO;
import com.airgear.dto.GoodsDto;
import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import com.airgear.model.goods.response.GoodsResponse;
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

    Set<Goods> getAllGoodsByUsername(String username);

    List<Goods> getAllGoods();

    Page<Goods> getAllGoods(Pageable pageable);

    Page<Goods> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsResponse> listGoodsByName(Pageable pageable, String goodsName);

    int getNewGoodsFromPeriod(OffsetDateTime fromDate, OffsetDateTime toDate);

    CountDeletedGoodsDTO countDeletedGoods(OffsetDateTime startDate, OffsetDateTime endDate, String category);

    Long getTotalNumberOfGoods();
    TotalNumberOfGoodsResponse getTotalNumberOfGoodsResponse();

    AmountOfGoodsByCategoryResponse getAmountOfGoodsByCategory();

    List<Goods> getRandomGoods(String categoryName, int quantity);

    //will return 12 similar goods (same category and similar price)
    Page<GoodsDto> getSimilarGoods(String categoryName, BigDecimal price);

    Map<Category, Long> getAmountOfNewGoodsByCategory(OffsetDateTime fromDate, OffsetDateTime toDate);

    void saveGoodsView(String ip, Long userId, Goods goods);


    GoodsDto createGoods(String username, GoodsDto goodsDto);

    GoodsDto addToFavorites(String username, Long goodsId);
}

