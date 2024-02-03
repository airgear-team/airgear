package com.airgear.service;

import com.airgear.model.Goods;

import java.util.Set;

public interface GoodsService {

    Goods getGoodsById(Long id);
    void deleteGoodsById(Long id);
    Goods saveGoods(Goods goods);
    Set<Goods> getAllGoodsByUsername(String username);

}
