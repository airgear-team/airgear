package com.airgear.service.impl;

import com.airgear.dto.ImagesSaveResponse;
import com.airgear.dto.UserDto;
import com.airgear.exception.ImageExceptions;
import com.airgear.service.ImageService;
import com.airgear.service.UserService;
import com.airgear.utils.DirectoryPathUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final long MAX_FILE_SIZE_IN_BYTES = 10485760;
    private static final String IMAGE_EXTENSIONS_PNG = "image/png";
    private static final String IMAGE_EXTENSIONS_JPEG = "image/jpeg";
    private static final String USER_DIR_NAME = "users";
    private static final String GOODS_DIR_NAME = "goods";
    private static final Path BASE_DIR = DirectoryPathUtil.getBasePath();

    private final UserService userService;

    @Override
    public ImagesSaveResponse uploadImages(String email, MultipartFile[] images, Long goodsId) {
        UserDto user = getUser(email);
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                log.info("Uploading: Name: " + image.getOriginalFilename() + ", Type: " + image.getContentType() + ", Size: " + image.getSize());
                String filePath = uploadImage(image, user.getId(), goodsId);
                imageUrls.add(filePath);
            } catch (IOException e) {
                log.error("Failed to upload photo", e);
                ImageExceptions.uploadImageProblem(image.getOriginalFilename());
            }
        }
        return new ImagesSaveResponse(imageUrls);
    }

    @Override
    public FileSystemResource downloadImage(String email, Long goodsId, String imageId) {
        UserDto user = getUser(email);
        String imagePath = DirectoryPathUtil.getBasePath() + "\\"+USER_DIR_NAME+"\\" + user.getId() + "\\"+GOODS_DIR_NAME+"\\" + goodsId + "\\" + imageId;
        log.info("image path : {}", imagePath);
        File file = new File(imagePath);
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            ImageExceptions.imageNotFound(user.getId(), goodsId, imageId);
            return new FileSystemResource("");
        }
    }

    @Override
    public String uploadImage(MultipartFile file, Long userId, Long goodsId) throws IOException {
        validateFile(file);
        Path targetDir = prepareTargetDirectory(userId, goodsId);
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = targetDir.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        return uniqueFileName;
    }

    private UserDto getUser(String email) {
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

    private Path prepareTargetDirectory(Long userId, Long goodsId) throws IOException {
        Path targetDir = BASE_DIR.resolve(USER_DIR_NAME)
                .resolve(userId.toString())
                .resolve(GOODS_DIR_NAME)
                .resolve(goodsId.toString());
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        return targetDir;
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
}
