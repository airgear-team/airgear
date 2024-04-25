package com.airgear.controller;

import com.airgear.dto.UserReviewDto;
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

    @PostMapping
    public ResponseEntity<UserReviewDto> createReview(@Valid @RequestBody UserReviewDto userReviewDto) {
        UserReviewDto createdReview = userReviewService.createReview(userReviewDto);
        return ResponseEntity.ok(createdReview);
    }
}
