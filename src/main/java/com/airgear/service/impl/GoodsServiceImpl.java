package com.airgear.service.impl;

import com.airgear.model.goods.Goods;
import com.airgear.model.goods.response.GoodsResponse;
import com.airgear.repository.GoodsRepository;
import com.airgear.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Set;

@Service(value = "goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Goods getGoodsById(Long id) {
        return goodsRepository.getReferenceById(id);
    }

    @Override
    public void deleteGoods(Goods goods) {
        goods.setDeletedAt(OffsetDateTime.now());
        goodsRepository.save(goods);
    }

    @Override
    public Goods saveGoods(@Valid Goods goods) {
        goods.setCreatedAt(OffsetDateTime.now());
        return goodsRepository.save(goods);
    }

    @Override
    public Goods updateGoods(Goods existingGoods) {
        existingGoods.setLastModified(OffsetDateTime.now());
        return goodsRepository.save(existingGoods);
    }

    @Override
    public Set<Goods> getAllGoodsByUsername(String username) {
        Set<Goods> goodsSet = goodsRepository.getGoodsByUserName(username);
        return goodsSet;
    }

    @Override
    public Page<GoodsResponse> listGoodsByName(Pageable pageable, String goodsName) {
        return goodsRepository.findAllByNameLikeIgnoreCase(pageable, goodsName)
                .map(GoodsResponse::fromGoods);
    }

}