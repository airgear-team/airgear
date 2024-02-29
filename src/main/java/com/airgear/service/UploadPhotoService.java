package com.airgear.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadPhotoService {
    String uploadPhoto(MultipartFile file) throws IOException;
}
