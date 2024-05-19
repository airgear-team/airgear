package com.airgear.controller;

import com.airgear.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/images")
    public Map<String, String> getCategoryImageUrls() {
        return categoryService.getAllCategoryImageUrls();
    }


    @GetMapping("/image/{categoryId}")
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable String categoryId) {
        String imagePath = "images/category/" + categoryId;

        try {
            ClassPathResource imgFile = new ClassPathResource(imagePath);
            if (!imgFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf("image/svg+xml"))
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

