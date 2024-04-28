package com.airgear.controller;

import com.airgear.dto.UserReviewCreateRequest;
import com.airgear.dto.UserReviewCreateResponse;
import com.airgear.service.UserReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping("/create")
    public ResponseEntity<UserReviewCreateResponse> createReview(@Valid @RequestBody UserReviewCreateRequest userReview) {
        UserReviewCreateResponse createdReview = userReviewService.createReview(userReview);
        return ResponseEntity.ok(createdReview);
    }
}