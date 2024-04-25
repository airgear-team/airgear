package com.airgear.controller;

import com.airgear.dto.ImagesSaveResponse;
import com.airgear.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/{goodsId}")
    public ResponseEntity<ImagesSaveResponse> uploadImages(@AuthenticationPrincipal String email,
                                                           @RequestParam("images") MultipartFile[] images,
                                                           @PathVariable("goodsId") Long goodsId) {
        return ResponseEntity.ok(imageService.uploadImages(email, images, goodsId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{goodsId}/image/{imageId}")
    public ResponseEntity<FileSystemResource> downloadImages(@AuthenticationPrincipal String email,
                                                             @PathVariable("goodsId") Long goodsId,
                                                             @PathVariable("imageId") String imageId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.downloadImage(email, goodsId, imageId));
    }
}
