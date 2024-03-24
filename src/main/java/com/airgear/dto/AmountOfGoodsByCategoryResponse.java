package com.airgear.dto;

import com.airgear.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class AmountOfGoodsByCategoryResponse {

    Map<Category, Long> categoryAmounts;
}
