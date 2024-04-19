package com.airgear.service;

import com.airgear.dto.ImagesSaveResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadPhotoService {
    String uploadImage(MultipartFile image, Long userId, Long goodsId) throws IOException;
    ImagesSaveResponse uploadImages(MultipartFile[] images, Long userId, Long goodsId);
}