package com.airgear.controller;

import com.airgear.service.GoogleDriveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GoogleDriveTestController {

//    @Autowired
//    private GoogleDriveTestService googleDriveService;
//
//    @GetMapping("/test-drive-upload")
//    public String testDriveUpload() {
//        try {
//            return googleDriveService.uploadTestFile();
//        } catch (IOException e) {
//            return "An error occurred: " + e.getMessage();
//        }
//    }
}