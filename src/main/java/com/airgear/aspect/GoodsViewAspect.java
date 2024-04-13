package com.airgear.aspect;

import com.airgear.dto.GoodsDto;
import com.airgear.service.GoodsService;
import com.airgear.service.GoodsViewService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@AllArgsConstructor
public class GoodsViewAspect {

    private final GoodsViewService goodsViewService;
    private final GoodsService goodsService;
    private final UserService userService;

    @Before("execution(* com.airgear.controller.GoodsController.getGoodsById(..)) && args(goodsId)")
    public void saveGoodsView(@PathVariable Long goodsId) {
        goodsViewService.saveGoodsView(getRemoteAddr(), getUserId(), getGoods(goodsId));
    }

    private GoodsDto getGoods(Long goodsId) {
        return goodsService.getGoodsById(goodsId);
    }

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByEmail(auth.getName()).getId();
    }

    private String getRemoteAddr() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
    }
}