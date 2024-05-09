package com.airgear.controller;

import com.airgear.dto.GoodsImagesResponse;
import com.airgear.dto.ImagesSaveResponse;
import com.airgear.mapper.GoodsImagesMapper;
import com.airgear.model.GoodsImages;
import com.airgear.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final GoodsImagesMapper goodsImagesMapper;

    @PostMapping("/images/{goodsId}")
    public ResponseEntity<ImagesSaveResponse> uploadImages(@AuthenticationPrincipal String email,
                                                           @RequestParam("images") MultipartFile[] images,
                                                           @PathVariable("goodsId") Long goodsId) {
        return ResponseEntity.ok(imageService.uploadImages(email, images, goodsId));
    }

    @GetMapping("/users/{userId}/goods/{goodsId}/images/{imageId}")
    public ResponseEntity<FileSystemResource> downloadImages(@PathVariable("userId") Long userId,
                                                             @PathVariable("goodsId") Long goodsId,
                                                             @PathVariable("imageId") String imageId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.downloadImage(userId, goodsId, imageId));
    }

    //todo переробоити бд та логіку отримання фото.
    // Отримання фото відбувається без авторизації
    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImagesByImageId(@PathVariable("imageId") String imageId) {
        try {
            byte[] imageBytes = imageService.getImageBytesById(imageId);
            MediaType mediaType = imageService.getImageMediaType(imageId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/goods/{goodsId}/images")
    public ResponseEntity<List<GoodsImagesResponse>> getImagesByGoodsId(@PathVariable Long goodsId) {
        List<GoodsImages> imageIds = imageService.getImagesByGoodsId(goodsId);
        return ResponseEntity.ok().body(goodsImagesMapper.toDtoList(imageIds));
    }

}