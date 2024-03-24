package com.airgear.service;

import com.airgear.dto.CheckoutDTO;
import com.airgear.model.goods.Goods;
import org.springframework.security.core.Authentication;

public interface LiqPayService {

    CheckoutDTO createCheckoutDtoPay(Goods goods, Authentication auth);

    String generatePaymentLink(CheckoutDTO checkoutDTO) throws IllegalAccessException;

}
