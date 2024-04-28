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
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping
    public ResponseEntity<UserReviewCreateResponse> create(@RequestBody @Valid UserReviewCreateRequest request,
                                                           UriComponentsBuilder ucb) {
        UserReviewCreateResponse response = userReviewService.createReview(request);
        return ResponseEntity
                .created(ucb.path("/reviews/{id}").build(response.getId()))
                .body(response);
    }
}
