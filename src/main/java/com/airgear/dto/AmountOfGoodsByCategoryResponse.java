package com.airgear.dto;

import com.airgear.model.goods.Category;
import lombok.Data;

import java.util.Map;

@Data
public class AmountOfGoodsByCategoryResponse {

    Map<Category, Long> categoryAmounts;

    public AmountOfGoodsByCategoryResponse(Map<Category, Long> categoryAmounts) {
        this.categoryAmounts = categoryAmounts;
    }
}
