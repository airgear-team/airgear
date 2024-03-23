package com.airgear.controller;

import com.airgear.dto.FeedbackDTO;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedback")
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) {
        feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}