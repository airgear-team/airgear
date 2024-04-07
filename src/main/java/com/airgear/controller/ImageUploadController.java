package com.airgear.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController("/images")
@AllArgsConstructor
public class ImageUploadController {

    //private final GoogleDriveServiceImpl googleDriveTestService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("imagePath") MultipartFile photo) {
//        try {
            log.info("Name: " + photo.getOriginalFilename());
            log.info("Type: " + photo.getContentType());
            log.info("Size: " + photo.getSize());
            //String photoId = googleDriveTestService.uploadPhoto(photo);
            return ResponseEntity.ok("Uploaded Photo ID: " + "photoId");
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to upload photo: " + e.getMessage());
//        }
    }

}
