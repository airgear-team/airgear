package com.airgear.service;

import com.airgear.dto.UserDto;
import com.airgear.dto.UserReviewDto;
import com.airgear.model.User;
import com.airgear.model.UserReview;

import java.util.List;

public interface UserReviewService {

    UserReview createReview(UserReviewDto userReviewDto);

    void updateReview(UserReview userReview);

    void deleteReview(UserReview userReview);

    List<UserReview> getReviewsForUser(User user);

}
