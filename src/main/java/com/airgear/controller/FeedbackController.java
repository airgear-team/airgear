package com.airgear.controller;

import com.airgear.dto.FeedbackDto;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<Void> submitFeedback(Authentication auth, @Valid @RequestBody FeedbackDto feedbackDTO) {
        feedbackService.createFeedback(auth.getName(), feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}