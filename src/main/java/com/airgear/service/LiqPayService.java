package com.airgear.service;

import com.airgear.dto.CheckoutDto;
import com.airgear.model.goods.Goods;
import org.springframework.security.core.Authentication;

public interface LiqPayService {

    CheckoutDto createCheckoutDtoPay(Goods goods, Authentication auth);

    String generatePaymentLink(CheckoutDto checkoutDTO) throws IllegalAccessException;

}
