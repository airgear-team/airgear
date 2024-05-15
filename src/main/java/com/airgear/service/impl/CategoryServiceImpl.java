package com.airgear.service.impl;

import com.airgear.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value = "categoryService")
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<String> getAllCategoryImageUrls() {
        return null;
    }
}
