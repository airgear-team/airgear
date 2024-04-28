package com.airgear.service;

import com.airgear.dto.GoodsGetResponse;

public interface GoodsViewService {

    void saveGoodsView(String ip, Long userId, GoodsGetResponse goods);
}