package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserReviewDto {
    private Long id;
    private UserDto reviewer;
    private UserDto reviewed;
    private Integer rating;
    private String comment;
    private OffsetDateTime createdAt;
}
