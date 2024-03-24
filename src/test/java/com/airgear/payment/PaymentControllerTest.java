package com.airgear.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.airgear.controller.PaymentController;
import com.airgear.dto.CheckoutDTO;
import com.airgear.model.goods.Goods;
import com.airgear.service.GoodsService;
import com.airgear.service.LiqPayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public class PaymentControllerTest {

    @Mock
    private LiqPayService liqPayService;

    @Mock
    private GoodsService goodsService;

    @Mock
    private Authentication auth;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRedirectToPaymentPage() throws IllegalAccessException {
        Long goodsId = 123L;
        Goods goods = new Goods();
        goods.setPrice(new BigDecimal(100.00));
        goods.setName("Test Goods");
        when(goodsService.getGoodsById(goodsId)).thenReturn(goods);

        CheckoutDTO checkoutDTO = CheckoutDTO.builder()
                .action("pay")
                .amount(new BigDecimal(100.00))
                .currency("UAH")
                .description("Test description")
                .version(3)
                .build();
        when(liqPayService.createCheckoutDtoPay(goods, auth)).thenReturn(checkoutDTO);

        String paymentLink = "https://test.payment.link";
        when(liqPayService.generatePaymentLink(checkoutDTO)).thenReturn(paymentLink);

        String redirectURL = paymentController.redirectToPaymentPage(goodsId, auth);

        assertNotNull(redirectURL);
        assertTrue(redirectURL.startsWith("redirect:"));
        assertTrue(redirectURL.contains(paymentLink));
    }
}
