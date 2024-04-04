package com.airgear.service.impl;

import com.airgear.model.Photo;
import com.airgear.repository.PhotoRepository;
import com.airgear.service.UploadPhotoService;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

//@Service
@AllArgsConstructor
public class GoogleDriveServiceImpl implements UploadPhotoService {

    private final Drive driveService;
    private final PhotoRepository photoRepository;

    @Override
    public String uploadPhoto(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        String mimeType = file.getContentType();
        fileMetadata.setMimeType(mimeType);

        String folderId = "1CWCmxWXB0qOk3DdYjtoEdFrbRJIQqZwU";
        fileMetadata.setParents(Collections.singletonList(folderId));

        FileContent mediaContent = new FileContent(mimeType, tempFile.toFile());
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink")
                .execute();

        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        String webViewLink = uploadedFile.getWebViewLink();
        Photo photo = new Photo();
        photo.setWebViewLink(webViewLink);
        photoRepository.save(photo);

        Files.delete(tempFile);

        return "Web View Link: " + webViewLink;
    }
}
