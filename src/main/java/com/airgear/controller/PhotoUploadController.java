package com.airgear.controller;
import com.airgear.service.GoogleDriveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoUploadController {

    @Autowired
    private GoogleDriveTestService googleDriveTestService;

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        try {
            String photoId = googleDriveTestService.uploadPhoto(photo);
            return ResponseEntity.ok("Uploaded Photo ID: " + photoId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to upload photo: " + e.getMessage());
        }
    }
}
