package com.airgear.service;

import com.airgear.dto.GoodsCreateRequest;

public interface GoodsViewService {

    void saveGoodsView(String ip, Long userId, GoodsCreateRequest goods);
}