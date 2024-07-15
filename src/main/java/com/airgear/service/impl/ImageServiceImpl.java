package com.airgear.service.impl;

import com.airgear.dto.ImageDownloadRequest;
import com.airgear.dto.ImagesSaveResponse;
import com.airgear.dto.UserGetResponse;
import com.airgear.exception.GoodsExceptions;
import com.airgear.exception.ImageExceptions;
import com.airgear.model.Goods;
import com.airgear.model.GoodsImages;
import com.airgear.repository.GoodsRepository;
import com.airgear.service.ImageService;
import com.airgear.service.UserService;
import com.airgear.utils.DirectoryPathUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final long MAX_FILE_SIZE_IN_BYTES = 10485760;
    private static final String IMAGE_EXTENSIONS_PNG = "image/png";
    private static final String IMAGE_EXTENSIONS_JPEG = "image/jpeg";
    private static final int MAX_IMAGES_COUNT = 30;

    private static final Path BASE_DIR = DirectoryPathUtil.getBasePath();
    private final UserService userService;
    private final GoodsRepository goodsRepository;

    @Override
    public ImagesSaveResponse uploadImages(String email, MultipartFile[] images, Long goodsId) {
        UserGetResponse user = getUser(email);
        List<GoodsImages> imagesList = new ArrayList<>();
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> GoodsExceptions.goodsNotFound(goodsId));

        List<GoodsImages> currentImages = goods.getImages();
        int totalImages = currentImages.size() + images.length;
        if (totalImages > MAX_IMAGES_COUNT) {
            throw ImageExceptions.tooManyImages(MAX_IMAGES_COUNT);
        }
        for (MultipartFile image : images) {
            try {
                log.info("Uploading: Name: {}, Type: {}, Size: {}", image.getOriginalFilename(), image.getContentType(), image.getSize());
                String filePath = uploadImage(image, user.getId(), goodsId);
                GoodsImages goodsImage = new GoodsImages();
                goodsImage.setImageUrl(filePath);
                imagesList.add(goodsImage);
            } catch (IOException e) {
                log.error("Failed to upload photo", e);
                ImageExceptions.uploadImageProblem(image.getOriginalFilename());

            }
        }
        currentImages.addAll(imagesList);
        goods.setImages(currentImages);
        goodsRepository.save(goods);
        List<String> imageUrls = imagesList.stream().map(GoodsImages::getImageUrl).collect(Collectors.toList());
        return new ImagesSaveResponse(imageUrls);
    }

    @Override
    public FileSystemResource downloadImage(ImageDownloadRequest request) {
        Path imagePath = Paths.get(DirectoryPathUtil.getBasePath().toString(), request.getImageId());
        log.info("image path : {}", imagePath);

        File file = imagePath.toFile();
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            ImageExceptions.imageNotFound(request.getUserId(), request.getGoodsId(), request.getImageId());
            return new FileSystemResource("");
        }
    }

    @Override
    public String uploadImage(MultipartFile file, Long userId, Long goodsId) throws IOException {
        validateFile(file);
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = BASE_DIR.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        return uniqueFileName;
    }

    private UserGetResponse getUser(String email) {
        return userService.getUserByEmail(email);
    }

    private void validateFile(MultipartFile image) {
        if (image.isEmpty() || image.getSize() > MAX_FILE_SIZE_IN_BYTES) {
            throw ImageExceptions.invalidImageSize(image.getOriginalFilename());
        }
        String contentType = image.getContentType();
        if (contentType == null ||
                !(contentType.equals(IMAGE_EXTENSIONS_PNG) ||
                        contentType.equals(IMAGE_EXTENSIONS_JPEG))) {
            throw ImageExceptions.invalidImageExtension(image.getOriginalFilename());
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = getExtension(originalFilename);
        return UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    @Override
    public byte[] getImageBytesById(String imageId) throws IOException {
        String imagePath = BASE_DIR.resolve(imageId).toString();
        return Files.readAllBytes(Path.of(imagePath));
    }

    @Override
    public List<GoodsImages> getImagesByGoodsId(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> GoodsExceptions.goodsNotFound(goodsId));
        return goods.getImages();
    }

    @Override
    public MediaType getImageMediaType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpeg", "jpg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}