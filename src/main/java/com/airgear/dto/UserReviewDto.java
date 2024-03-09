package com.airgear.dto;

import com.airgear.model.UserReview;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private UserDto reviewedUser;
    private int rating;
    private String comment;
    private OffsetDateTime createdAt;

    public UserReview toUserReview() {
        return UserReview.builder()
                .id(id)
                .reviewer(reviewer.toUser())
                .reviewedUser(reviewedUser.toUser())
                .rating(rating)
                .comment(comment)
                .createdAt(createdAt)
                .build();
    }

    public static List<UserReview> toUserReviews(List<UserReviewDto> userReviews) {
        List<UserReview> result = new ArrayList<>();
        userReviews.forEach(userReviewDto -> result.add(userReviewDto.toUserReview()));
        return result;
    }

    public static UserReviewDto fromUserReview(UserReview userReview) {
        return UserReviewDto.builder()
                .id(userReview.getId())
                .reviewer(UserDto.fromUser(userReview.getReviewer()))
                .reviewedUser(UserDto.fromUser(userReview.getReviewedUser()))
                .rating(userReview.getRating())
                .comment(userReview.getComment())
                .createdAt(userReview.getCreatedAt())
                .build();
    }

    public static List<UserReviewDto> fromUserReviews(List<UserReview> userReviews) {
        List<UserReviewDto> result = new ArrayList<>();
        userReviews.forEach(userReview -> result.add(UserReviewDto.fromUserReview(userReview)));
        return result;
    }


}
