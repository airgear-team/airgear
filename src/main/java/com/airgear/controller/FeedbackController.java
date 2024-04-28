package com.airgear.controller;

import com.airgear.dto.FeedbackCreateRequest;
import com.airgear.dto.FeedbackCreateResponse;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FeedbackCreateResponse> create(@AuthenticationPrincipal String email,
                                                                  @RequestBody @Valid FeedbackCreateRequest request,
                                                                  UriComponentsBuilder ucb) {
        FeedbackCreateResponse response = feedbackService.create(email, request);
        return ResponseEntity
                .created(ucb.path("/feedbacks/{id}").build(response.getId()))
                .body(response);
    }
}
