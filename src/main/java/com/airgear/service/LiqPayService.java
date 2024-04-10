package com.airgear.service;

import com.airgear.dto.CheckoutDto;
import com.airgear.dto.GoodsDto;
import org.springframework.security.core.Authentication;

public interface LiqPayService {

    CheckoutDto createCheckoutDtoPay(GoodsDto goods, Authentication auth);

    String generatePaymentLink(CheckoutDto checkoutDTO) throws IllegalAccessException;

}
