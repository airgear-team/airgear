package com.airgear.service.impl;

import com.airgear.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "categoryService")
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String BASE_URL = "/category/image/";

    private static final Map<String, String> CATEGORY_IMAGES = new HashMap<>();

    static {
        CATEGORY_IMAGES.put("Нерухомість", BASE_URL + "1.svg");
        CATEGORY_IMAGES.put("Транспорт і спеціальне обладнання", BASE_URL + "2.svg");
        CATEGORY_IMAGES.put("Техніка та електроніка", BASE_URL + "3.svg");
        CATEGORY_IMAGES.put("Одяг та аксесуари", BASE_URL + "4.svg");
        CATEGORY_IMAGES.put("Обладнання для відпочинку", BASE_URL + "5.svg");
        CATEGORY_IMAGES.put("Інструмент та обладнання", BASE_URL + "6.svg");
        CATEGORY_IMAGES.put("Спортивне обладнання", BASE_URL + "7.svg");
        CATEGORY_IMAGES.put("Краса та здоров'я", BASE_URL + "8.svg");
        CATEGORY_IMAGES.put("Товари для подій", BASE_URL + "9.svg");
    }

    @Override
    public Map<String, String> getAllCategoryImageUrls() {
        return CATEGORY_IMAGES;
    }
}
