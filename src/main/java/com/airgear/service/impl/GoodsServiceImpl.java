package com.airgear.service.impl;

import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import com.airgear.repository.GoodsRepository;
import com.airgear.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public Page<Goods> getAllGoods(Pageable pageable) {
        return goodsRepository.findAll(pageable);
    }

    @Override
    public Page<Goods> filterGoods(Category category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (category != null && minPrice != null && maxPrice != null) {
            return goodsRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageable);
        } else if (minPrice != null && maxPrice != null) {
            return goodsRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (minPrice != null) {
            return goodsRepository.findByPriceGreaterThan(minPrice, pageable);
        } else if (maxPrice != null) {
            return goodsRepository.findByPriceLessThan(maxPrice, pageable);
        } else if (category != null) {
            return goodsRepository.findByCategory(category, pageable);
        } else {
            return goodsRepository.findAll(pageable);
        }
    }

}