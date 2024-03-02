package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewDto {
    private Long id;
    private Long reviewerUserId;
    private Long reviewedUserId;

    @Min(value = 0, message = "Stars must be at least 0")
    @Max(value = 5, message = "Stars must be at most 5")
    private Integer stars;

    private String comment;
    private OffsetDateTime createdAt;
}
