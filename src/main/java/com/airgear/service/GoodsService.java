package com.airgear.service;

import com.airgear.dto.CountDeletedGoodsDTO;
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

    void deleteGoods(Goods goods);

    Goods saveGoods(Goods goods);

    Goods updateGoods(Goods goods);

    Set<Goods> getAllGoodsByUsername(String username);

    List<Goods> getAllGoods();

    Page<Goods> getAllGoods(Pageable pageable);

    Page<Goods> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<GoodsResponse> listGoodsByName(Pageable pageable, String goodsName);

    int getNewGoodsFromPeriod(OffsetDateTime fromDate, OffsetDateTime toDate);

    CountDeletedGoodsDTO countDeletedGoods(OffsetDateTime startDate, OffsetDateTime endDate, String category);

    Long getTotalNumberOfGoods();

    Map<Category, Long> getAmountOfGoodsByCategory();

    List<Goods> getRandomGoods(String categoryName, int quantity);

    void saveGoodsView(String ip, Long userId, Goods goods);
}