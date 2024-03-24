package com.airgear.controller;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.UserReview;
import com.airgear.service.UserReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews") // TODO use Routes.REVIEWS
@AllArgsConstructor
public class UserReviewController {
    private final UserReviewService userReviewService;

    @PostMapping("/create")
    public ResponseEntity<UserReview> createReview(@Valid @RequestBody UserReviewDto userReviewDto) { // TODO use UserReview DTO
        UserReview createdReview = userReviewService.createReview(userReviewDto);
        return ResponseEntity.ok(createdReview);
    }
}

