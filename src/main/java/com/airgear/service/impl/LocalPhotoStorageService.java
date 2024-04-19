package com.airgear.service.impl;

import com.airgear.service.UploadPhotoService;
import com.airgear.utils.DirectoryPathUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalPhotoStorageService implements UploadPhotoService {
    private static final long MAX_FILE_SIZE = 10485760; // 10 MB
    private static final Path BASE_DIR = DirectoryPathUtil.getBasePath();

    @Override
    public String uploadPhoto(MultipartFile file, Long userId, Long goodsId) throws IOException {
        validateFile(file);
        Path targetDir = prepareTargetDirectory(userId, goodsId);
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = targetDir.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        return filePath.toString();
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("Invalid file size: must not exceed 10 MB and must not be empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            throw new IOException("Invalid file type: only JPEG and PNG are allowed.");
        }
    }

    private Path prepareTargetDirectory(Long userId, Long goodsId) throws IOException {
        Path targetDir = BASE_DIR.resolve("images")
                .resolve(userId.toString())
                .resolve("goods")
                .resolve(goodsId.toString());
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        return targetDir;
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = getExtension(originalFilename);
        return UUID.randomUUID().toString() + (extension.isEmpty() ? "" : "." + extension);
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}