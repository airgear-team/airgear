package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewGetRequest {
    private Long id;
    private UserGetRequest reviewer;
    private UserGetRequest reviewed;
    private Integer rating;
    private String comment;
    private OffsetDateTime createdAt;
}
