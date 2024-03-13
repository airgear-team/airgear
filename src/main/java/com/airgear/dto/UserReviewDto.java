package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * UserReviewDto class. Fields are similar to UserReview entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class UserReviewDto {
    private Long id;
    private UserDto reviewer;
    private UserDto reviewed;
    private int rating;
    private String comment;
    private OffsetDateTime createdAt;
}
