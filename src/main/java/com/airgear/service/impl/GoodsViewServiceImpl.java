package com.airgear.service.impl;

import com.airgear.model.GoodsView;
import com.airgear.model.goods.Goods;
import com.airgear.repository.GoodsViewRepository;
import com.airgear.service.GoodsViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service("goodsViewService")
@AllArgsConstructor
public class GoodsViewServiceImpl implements GoodsViewService {

    private final GoodsViewRepository goodsViewRepository;

    @Override
    public void saveGoodsView(String ip, Long userId, Goods goods) {
        if (goodsViewRepository.existsByIpAndGoods(ip, goods)) {
            if (userId != null && !goodsViewRepository.existsByUserIdAndGoods(userId, goods)) {
                goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
            }
            return;
        }
        goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
    }
}