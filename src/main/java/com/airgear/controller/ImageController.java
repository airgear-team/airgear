package com.airgear.controller;

import com.airgear.service.impl.LocalPhotoStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {
    private final LocalPhotoStorageService uploadPhotoService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<String> uploadImages(@RequestParam("imagePath") MultipartFile[] photos,
                                               @RequestParam("userId") Long userId,
                                               @RequestParam("goodsId") Long goodsId) {
        StringBuilder result = new StringBuilder();
        for (MultipartFile photo : photos) {
            try {
                log.info("Uploading: Name: " + photo.getOriginalFilename() + ", Type: " + photo.getContentType() + ", Size: " + photo.getSize());
                uploadPhotoService.uploadPhoto(photo, userId, goodsId);
                result.append("Photo uploaded successfully");
            } catch (IOException e) {
                log.error("Failed to upload photo", e);
                result.append("Failed to upload photo: ").append(" - ").append(e.getMessage()).append("\n");
            }
        }
        return ResponseEntity.ok(result.toString());
    }
}