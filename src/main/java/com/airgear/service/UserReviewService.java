package com.airgear.service;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.User;

import java.util.List;

public interface UserReviewService {

    UserReviewDto createReview(UserReviewDto userReviewDto);

    void updateReview(UserReviewDto userReview);

    void deleteReview(UserReviewDto userReview);

    List<UserReviewDto> getReviewsForUser(User user);

}
