package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class AmountOfGoodsByCategoryResponse {

    Map<CategoryDto, Long> categoryAmounts;
}
