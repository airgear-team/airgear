package com.airgear.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.airgear.controller.PaymentController;
import com.airgear.dto.GoodsDto;
import com.airgear.model.Price;
import com.airgear.model.Currency;
import com.airgear.model.PriceType;
import com.airgear.service.GoodsService;
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
        GoodsDto goods = GoodsDto.builder().build();
        goods.setPrice(new Price(BigDecimal.valueOf(100.00), Currency.UAH, PriceType.NEGOTIATED_PRICE));
        goods.setName("Test Goods");
        when(goodsService.getGoodsById(goodsId)).thenReturn(goods);

        CheckoutDto checkoutDTO = CheckoutDto.builder()
                .action("pay")
                .amount(BigDecimal.valueOf(100.00))
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
