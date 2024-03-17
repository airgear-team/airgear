package com.airgear.service;

import com.airgear.dto.CheckoutDTO;
import com.airgear.model.goods.Goods;
import org.springframework.security.core.Authentication;

public interface LiqPayService {

    public CheckoutDTO createCheckoutDtoPay(Goods goods, Authentication auth);

    public String generatePaymentLink(CheckoutDTO checkoutDTO) throws IllegalAccessException;

}
