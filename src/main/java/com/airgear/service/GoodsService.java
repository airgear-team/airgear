package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.model.Goods;

import java.util.Set;

public interface GoodsService {

    Goods getGoodsById(Long id);
    void deleteGoodsById(Long id);
    Goods saveGoods(GoodsDto goods);
    Goods updateGoods(Goods goods);
    Set<Goods> getAllGoodsByUsername(String username);

}
