package com.airgear.service.impl;

import com.airgear.dto.GoodsDto;
import com.airgear.model.Goods;
import com.airgear.repository.GoodsRepository;
import com.airgear.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Set;

@Service(value = "goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Goods getGoodsById(Long id){
        return goodsRepository.getReferenceById(id);
    }

    @Override
    public void deleteGoodsById(Long id) {
        goodsRepository.deleteById(id);
    }

    @Override
    public Goods saveGoods(@Valid GoodsDto goodsDto) {
        Goods goods = goodsDto.getGoodsFromDto();
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

}