package com.airgear.service;

import com.airgear.model.goods.Goods;

public interface GoodsViewService {

    void saveGoodsView(String ip, Long userId, Goods goods);
}