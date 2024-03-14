package com.airgear.aspects;

import com.airgear.model.goods.Goods;
import com.airgear.service.GoodsService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GoodsViewAspect {

    private final GoodsService goodsService;

    @Autowired
    public GoodsViewAspect(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Before("execution(* com.airgear.controller.GoodsController.getGoodsById(..)) && args(ip, userId, goods)")
    public void countUniqueViews(String ip, Long userId, Goods goods) {
        goodsService.saveGoodsView(ip, userId, goods);
    }
}