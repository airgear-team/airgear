package com.airgear.service.impl;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.User;
import com.airgear.model.UserReview;
import com.airgear.repository.UserRepository;
import com.airgear.repository.UserReviewRepository;
import com.airgear.service.UserReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userReviewService")
public class UserReviewServiceImpl implements UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;

    public UserReviewServiceImpl(UserReviewRepository userReviewRepository, UserRepository userRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userRepository = userRepository;
    }

    public UserReview createReview(UserReviewDto userReviewDto) {
        UserReview userReview = new UserReview();
        userReview.setRating(userReviewDto.getRating());
        userReview.setComment(userReviewDto.getComment());
        userReview.setCreatedAt(userReviewDto.getCreatedAt());

        User reviewer = userRepository.findById(userReviewDto.getReviewer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));

        User reviewedUser = userRepository.findById(userReviewDto.getReviewed().getId())
                .orElseThrow(() -> new IllegalArgumentException("Reviewed user not found"));

        userReview.setReviewer(reviewer);
        userReview.setReviewedUser(reviewedUser);
        userReview = userReviewRepository.save(userReview);

        Float averageRating = userReviewRepository.calculateAverageRatingForUser(reviewedUser.getId());
        reviewedUser.setRating(averageRating);
        userRepository.save(reviewedUser);

        return userReview;
    }
    public void updateReview(UserReview userReview) {
        userReviewRepository.save(userReview);
    }

    public void deleteReview(UserReview userReview) {
        userReviewRepository.delete(userReview);
    }

    public List<UserReview> getReviewsForUser(User user) {
        return userReviewRepository.findByReviewedUser(user);
    }

}