package com.airgear.service;

import com.airgear.model.goods.Goods;
import com.airgear.model.goods.response.GoodsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface GoodsService {

    Goods getGoodsById(Long id);

    void deleteGoods(Goods goods);

    Goods saveGoods(Goods goods);

    Goods updateGoods(Goods goods);

    Set<Goods> getAllGoodsByUsername(String username);

    Page<GoodsResponse> listGoodsByName(Pageable pageable, String goodsName);

}
