package com.airgear.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Service
public class GoogleDriveTestService {

    @Autowired
    private Drive driveService;

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
                .setFields("id, parents")
                .execute();

        Files.delete(tempFile);

        return "Photo ID: " + uploadedFile.getId();
    }
}
