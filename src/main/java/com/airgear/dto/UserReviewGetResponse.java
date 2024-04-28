package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserReviewGetResponse {
    private Long id;
    private UserGetResponse reviewer;
    private UserGetResponse reviewed;
    private Integer rating;
    private String comment;
    private OffsetDateTime createdAt;
}
