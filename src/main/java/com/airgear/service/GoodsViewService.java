package com.airgear.service;

import com.airgear.dto.GoodsDto;

public interface GoodsViewService {

    void saveGoodsView(String ip, Long userId, GoodsDto goods);
}