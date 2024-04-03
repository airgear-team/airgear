package com.airgear.controller;

import com.airgear.dto.MessageDto;
import com.airgear.service.impl.GoogleDriveServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@RestController
@AllArgsConstructor
public class PhotoUploadController {

    private final GoogleDriveServiceImpl googleDriveTestService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        try {
            String photoId = googleDriveTestService.uploadPhoto(photo);
            return ResponseEntity.ok(new MessageDto("Uploaded Photo ID: " + photoId));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new IOException("Failed to upload photo: " + e.getMessage()));
        }
    }
}
