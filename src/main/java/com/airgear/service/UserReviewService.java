package com.airgear.service;

import com.airgear.dto.*;
import com.airgear.model.User;

import java.util.List;

public interface UserReviewService {

    UserReviewCreateResponse createReview(UserReviewCreateRequest userReviewDto);

    UserReviewUpdateResponse updateReview(UserReviewUpdateRequest userReview);

    void deleteReview(UserReviewGetRequest userReview);

    List<UserReviewGetResponse> getReviewsForUser(User user);

}
