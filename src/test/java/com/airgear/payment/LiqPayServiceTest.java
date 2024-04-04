package com.airgear.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.airgear.dto.CheckoutDto;
import com.airgear.model.goods.Goods;
import com.airgear.repository.CheckoutRepository;
import com.airgear.service.impl.LiqPayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public class LiqPayServiceTest {

    @Mock
    private CheckoutRepository checkoutRepository;

    @InjectMocks
    private LiqPayServiceImpl liqPayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCheckoutDtoPay() {
        Goods goods = new Goods();
        goods.setPrice(new BigDecimal(100.00));
        goods.setName("Test Goods");
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("TestUser");

        CheckoutDto checkoutDTO = liqPayService.createCheckoutDtoPay(goods, auth);

        assertNotNull(checkoutDTO);
        assertEquals("pay", checkoutDTO.getAction());
        assertEquals(new BigDecimal(100.00), checkoutDTO.getAmount());
        assertEquals("UAH", checkoutDTO.getCurrency());
        assertEquals("TestUser rented Test Goods. Action: pay", checkoutDTO.getDescription());
        assertEquals(3, checkoutDTO.getVersion());
    }

    @Test
    void testGeneratePaymentLink() throws IllegalAccessException {
        CheckoutDto checkoutDTO = CheckoutDto.builder()
                .action("pay")
                .amount(new BigDecimal(100.00))
                .currency("UAH")
                .description("Test description")
                .version(3)
                .build();
        when(checkoutRepository.save(any())).thenReturn(null);

        String paymentLink = liqPayService.generatePaymentLink(checkoutDTO);

        assertNotNull(paymentLink);
        assertTrue(paymentLink.contains("<form"));
        assertTrue(paymentLink.contains("<sdk"));
    }
}