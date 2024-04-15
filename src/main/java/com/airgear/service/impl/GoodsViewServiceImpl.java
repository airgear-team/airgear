package com.airgear.service.impl;

import com.airgear.dto.GoodsDto;
import com.airgear.mapper.GoodsMapper;
import com.airgear.model.GoodsView;
import com.airgear.model.Goods;
import com.airgear.repository.GoodsViewRepository;
import com.airgear.service.GoodsViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service("goodsViewService")
@AllArgsConstructor
public class GoodsViewServiceImpl implements GoodsViewService {

    private final GoodsViewRepository goodsViewRepository;
    private final GoodsMapper goodsMapper;

    @Override
    public void saveGoodsView(String ip, Long userId, GoodsDto goodsDto) {
        Goods goods = goodsMapper.toModel(goodsDto);
        if (goodsViewRepository.existsByIpAndGoods(ip, goods)) {
            if (userId != null && !goodsViewRepository.existsByUserIdAndGoods(userId, goods)) {
                goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
            }
            return;
        }
        goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
    }
}