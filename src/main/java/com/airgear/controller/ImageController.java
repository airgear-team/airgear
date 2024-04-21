package com.airgear.controller;

import com.airgear.dto.ImagesSaveResponse;
import com.airgear.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/users/{userId}/goods/{goodsId}/images")
    public ResponseEntity<ImagesSaveResponse> uploadImages(@RequestParam("images") MultipartFile[] images,
                                               @PathVariable("userId") Long userId,
                                               @PathVariable("goodsId") Long goodsId) {
        return ResponseEntity.ok(imageService.uploadImages(images, userId, goodsId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/users/{userId}/goods/{goodsId}/images/{imageId}")
    public ResponseEntity<FileSystemResource> downloadImages(@PathVariable("userId") Long userId,
                                                             @PathVariable("goodsId") Long goodsId,
                                                             @PathVariable("imageId") String imageId) {
        return ResponseEntity.ok()
                             .contentType(MediaType.IMAGE_JPEG)
                             .body(imageService.downloadImage(userId, goodsId, imageId));
    }
}