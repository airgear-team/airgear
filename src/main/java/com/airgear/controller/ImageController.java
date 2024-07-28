package com.airgear.controller;

import com.airgear.dto.GoodsImagesResponse;
import com.airgear.dto.ImagesSaveResponse;
import com.airgear.mapper.GoodsImagesMapper;
import com.airgear.model.GoodsImages;
import com.airgear.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@Slf4j
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final GoodsImagesMapper goodsImagesMapper;

    @PostMapping("/{goodsId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ImagesSaveResponse uploadImages(@AuthenticationPrincipal String email,
                                           @RequestParam("images") MultipartFile[] images,
                                           @PathVariable("goodsId") Long goodsId) {
        return imageService.uploadImages(email, images, goodsId);
    }

    @GetMapping("/user/{userId}/goods/{goodsId}/image/{imageId}")
    public ResponseEntity<FileSystemResource> downloadImages(@PathVariable Long userId,
                                                             @PathVariable Long goodsId,
                                                             @PathVariable String imageId) {
        FileSystemResource resource = imageService.downloadImage(userId, goodsId, imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/{imageUrl}")
    public ResponseEntity<byte[]> getImageByImageUrl(@PathVariable("imageUrl") String imageUrl) {
        try {
            byte[] imageBytes = imageService.getImageBytesById(imageUrl);
            MediaType mediaType = imageService.getImageMediaType(imageUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{goodsId}/images")
    public List<GoodsImagesResponse> getImagesByGoodsId(@PathVariable Long goodsId) {
        List<GoodsImages> imageIds = imageService.getImagesByGoodsId(goodsId);
        return goodsImagesMapper.toDtoList(imageIds);
    }
}
