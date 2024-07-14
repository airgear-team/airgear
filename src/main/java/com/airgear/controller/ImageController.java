package com.airgear.controller;

import com.airgear.dto.GoodsImagesResponse;
import com.airgear.dto.ImageDownloadRequest;
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

import javax.validation.Valid;
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
    public ResponseEntity<ImagesSaveResponse> uploadImages(@AuthenticationPrincipal String email,
                                                           @RequestParam("images") MultipartFile[] images,
                                                           @PathVariable("goodsId") Long goodsId) {
        return ResponseEntity.ok(imageService.uploadImages(email, images, goodsId));
    }

    @GetMapping
    public ResponseEntity<FileSystemResource> downloadImages(@RequestBody @Valid ImageDownloadRequest request) {
        FileSystemResource resource = imageService.downloadImage(request);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    //todo переробоити бд та логіку отримання фото.
    // Отримання фото відбувається без авторизації
    @GetMapping("/{imageId}")
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

    @GetMapping("/{goodsId}/goods")
    public ResponseEntity<List<GoodsImagesResponse>> getImagesByGoodsId(@PathVariable Long goodsId) {
        List<GoodsImages> imageIds = imageService.getImagesByGoodsId(goodsId);
        return ResponseEntity.ok().body(goodsImagesMapper.toDtoList(imageIds));
    }
}
