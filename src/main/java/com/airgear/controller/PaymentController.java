package com.airgear.controller;

import com.airgear.dto.CheckoutDTO;
import com.airgear.model.goods.Goods;
import com.airgear.service.GoodsService;
import com.airgear.service.LiqPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.HTML;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final LiqPayService liqPayService;
    private final GoodsService goodsService;

    @Autowired
    public PaymentController(LiqPayService liqPayService, GoodsService goodsService) {
        this.liqPayService = liqPayService;
        this.goodsService = goodsService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/pay/{goodsId}")
    public String redirectToPaymentPage(@PathVariable Long goodsId, Authentication auth) throws IllegalAccessException {
        Goods good = goodsService.getGoodsById(goodsId);
        CheckoutDTO checkoutDTO = liqPayService.createCheckoutDtoPay(good, auth);
        String paymentLink = liqPayService.generatePaymentLink(checkoutDTO);
        return "redirect:" + paymentLink;
    }
}
