package com.airgear.service;

import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Set;

public interface GoodsService {

    Goods getGoodsById(Long id);
    void deleteGoodsById(Long id);
    Goods saveGoods(Goods goods);
    Set<Goods> getAllGoodsByUsername(String username);
    Page<Goods> getAllGoods(Pageable pageable);
    Page<Goods> filterGoods(Category category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}
