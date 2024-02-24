package com.airgear.service.impl;

import com.airgear.model.Goods;
import com.airgear.repository.GoodsRepository;
import com.airgear.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
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
    public Goods saveGoods(@Valid Goods goods) {
        Goods savedGoods = goodsRepository.save(goods);
        return savedGoods;
    }

    @Override
    public Set<Goods> getAllGoodsByUsername(String username) {
        Set<Goods> goodsSet = goodsRepository.getGoodsByUserName(username);
        return goodsSet;
    }
    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = goodsRepository.findAll();
        return goodsList;
    }
    @Override
    public List<Goods> getRandomGoods(int goodsQuantity) {
        return goodsRepository.getRandomGoods(goodsQuantity);
    }
}