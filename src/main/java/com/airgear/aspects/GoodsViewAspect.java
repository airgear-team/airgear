package com.airgear.aspects;

import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class GoodsViewAspect {

    private final GoodsService goodsService;
    private final UserService userService;

    @Autowired
    public GoodsViewAspect(GoodsService goodsService, UserService userService) {
        this.goodsService = goodsService;
        this.userService = userService;
    }

    @Before("execution(* com.airgear.controller.GoodsController.getGoodsById(..)) && args(goodsId)")
    public void saveGoodsView(@PathVariable Long goodsId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        goodsService.saveGoodsView(request.getRemoteAddr(), user.getId(), goods);
    }
}