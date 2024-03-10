package com.airgear.controller;

import com.airgear.dto.UserReviewDto;
import com.airgear.service.UserReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class UserReviewController {
    private final UserReviewService userReviewService;

    public UserReviewController(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }

    //TODO Створити модель а не повертати просто рядок
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@Valid @RequestBody UserReviewDto userReviewDto) {
        userReviewService.createReview(userReviewDto);
        return ResponseEntity.ok("Review created successfully.");
    }
}
