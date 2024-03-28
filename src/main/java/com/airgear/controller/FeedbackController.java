package com.airgear.controller;

import com.airgear.dto.FeedbackDto;
import com.airgear.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService=feedbackService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeedback(@Valid @RequestBody FeedbackDto feedbackDTO) {
        feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}