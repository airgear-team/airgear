package com.airgear.service.impl;

import com.airgear.dto.CheckoutDTO;
import com.airgear.model.goods.Goods;
import com.airgear.repository.CheckoutRepository;
import com.airgear.service.LiqPayService;
import com.liqpay.LiqPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LiqPayServiceImpl implements LiqPayService {

    private final CheckoutRepository checkoutRepository;
    private final String TEST_PUBLIC_KEY = "sandbox_i49078650453";
    private final String TEST_PRIVATE_KEY = "sandbox_RehhVzMcNFoVDkbUSLA6DoOuXUjsJdR2IQLKPFEU";
    private final LiqPay liqPay = new LiqPay(TEST_PUBLIC_KEY, TEST_PRIVATE_KEY);

    @Autowired
    public LiqPayServiceImpl(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    /**
     * This method creates CheckoutDTO from good with action pay (one payment).
     * In future we can develop other payment methods (subscritpions, holdings, paydonates)
     * <p>
     * For more information read the docs
     * <a href="https://www.liqpay.ua/documentation/api/aquiring/checkout/doc">LiqPay docs</a>
     *
     * @param goods
     * @param auth
     * @return CheckoutDTO
     */
    @Override
    public CheckoutDTO createCheckoutDtoPay(Goods goods, Authentication auth) {
        return CheckoutDTO.builder()
                .action("pay")
                .amount(goods.getPrice())
                .currency("UAH") // for other currencies we need to add currency attribute to model in future
                .description(auth.getName() + " rented " + goods.getName() + ". Action: pay")
                .version(3)
                .build();
    }

    @Override
    public String generatePaymentLink(CheckoutDTO checkoutDTO) throws IllegalAccessException {
        checkoutRepository.save(checkoutDTO.toModel());
        Map<String, String> params = checkoutDTO.toMap();
        return liqPay.cnb_form(params);
    }

}

