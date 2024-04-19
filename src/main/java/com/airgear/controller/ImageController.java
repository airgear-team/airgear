package com.airgear.controller;

import com.airgear.dto.ImagesSaveResponse;
import com.airgear.service.UploadPhotoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ImageController {

    private final UploadPhotoService uploadPhotoService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/{userId}/goods/{goodsId}/images")
    public ResponseEntity<ImagesSaveResponse> uploadImages(@RequestParam("images") MultipartFile[] images,
                                               @PathVariable("userId") Long userId,
                                               @PathVariable("goodsId") Long goodsId) {
        return ResponseEntity.ok(uploadPhotoService.uploadImages(images, userId, goodsId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{userId}/goods/{goodsId}/images/{imageId}")
    public ResponseEntity<String> downloadImages(@PathVariable("userId") Long userId,
                                            @PathVariable("goodsId") Long goodsId,
                                            @PathVariable("imageId") Long imageId) {
        return ResponseEntity.ok("ok");
    }

}