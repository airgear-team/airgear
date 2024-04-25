package com.airgear.service;

import com.airgear.dto.ImagesSaveResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    ImagesSaveResponse uploadImages(String email, MultipartFile[] images, Long goodsId);

    FileSystemResource downloadImage(String email, Long goodsId, String imageId);

    String uploadImage(MultipartFile image, Long userId, Long goodsId) throws IOException;

}
