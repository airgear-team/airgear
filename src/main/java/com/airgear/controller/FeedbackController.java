package com.airgear.controller;

import com.airgear.dto.FeedbackResponse;
import com.airgear.dto.FeedbackSaveRequest;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FeedbackResponse> create(@AuthenticationPrincipal String email,
                                                   @RequestBody @Valid FeedbackSaveRequest request,
                                                   UriComponentsBuilder ucb) {
        FeedbackResponse response = feedbackService.create(email, request);
        return ResponseEntity
                .created(ucb.path("/feedbacks/{id}").build(response.getId()))
                .body(response);
    }
}
