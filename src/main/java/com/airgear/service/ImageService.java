package com.airgear.service;

import com.airgear.dto.ImageDownloadRequest;
import com.airgear.dto.ImagesSaveResponse;
import com.airgear.model.GoodsImages;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ImagesSaveResponse uploadImages(String email, MultipartFile[] images, Long goodsId);

    FileSystemResource downloadImage(ImageDownloadRequest request);

    String uploadImage(MultipartFile image, Long userId, Long goodsId) throws IOException;

    byte[] getImageBytesById(String imageId) throws IOException;

    List<GoodsImages> getImagesByGoodsId(Long goodsId);

    MediaType getImageMediaType(String fileName);

}
