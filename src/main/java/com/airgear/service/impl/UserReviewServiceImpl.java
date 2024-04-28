package com.airgear.service.impl;

import com.airgear.dto.*;
import com.airgear.exception.UserExceptions;
import com.airgear.exception.UserReviewExceptions;
import com.airgear.mapper.UserReviewMapper;
import com.airgear.model.User;
import com.airgear.model.UserReview;
import com.airgear.repository.UserRepository;
import com.airgear.repository.UserReviewRepository;
import com.airgear.service.UserReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userReviewService")
@AllArgsConstructor
public class UserReviewServiceImpl implements UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final UserReviewMapper userReviewMapper;

    @Override
    public UserReviewCreateResponse createReview(UserReviewCreateRequest userReviewDto) {
        if (userReviewDto.getReviewed().getId().equals(userReviewDto.getReviewer().getId())){
            throw UserReviewExceptions.reviewerNotReviewed(userReviewDto.getReviewer().getId());
        }
        User reviewer = userRepository.findById(userReviewDto.getReviewer().getId())
                .orElseThrow(() -> UserExceptions.userNotFound(userReviewDto.getReviewer().getId()));

        User reviewedUser = userRepository.findById(userReviewDto.getReviewed().getId())
                .orElseThrow(() -> UserExceptions.userNotFound(userReviewDto.getReviewed().getId()));

        if(userReviewRepository.countByReviewedAndByReviewer(reviewedUser, reviewer)>0){
            throw UserReviewExceptions.onlyOneReview(reviewer.getId());
        }

        UserReview userReview = new UserReview();
        userReview.setRating(userReviewDto.getRating());
        userReview.setComment(userReviewDto.getComment());
        userReview.setCreatedAt(userReviewDto.getCreatedAt());

        userReview.setReviewer(reviewer);
        userReview.setReviewedUser(reviewedUser);
        userReview = userReviewRepository.save(userReview);

        Float averageRating = userReviewRepository.calculateAverageRatingForUser(reviewedUser.getId());
        reviewedUser.setRating(averageRating);
        userRepository.save(reviewedUser);

        return userReviewMapper.toCreateResponse(userReview);
    }

    @Override
    public UserReviewUpdateResponse updateReview(UserReviewUpdateRequest userReview) {
        return userReviewMapper.toUpdateResponse(userReviewRepository.save(userReviewMapper.toModel(userReview)));
    }

    @Override
    public void deleteReview(UserReviewGetRequest userReview) {
        userReviewRepository.delete(userReviewMapper.toModel(userReview));
    }

    @Override
    public List<UserReviewGetResponse> getReviewsForUser(User user) {
        return userReviewRepository.findByReviewedUser(user)
                .stream()
                .map(userReviewMapper::toGetResponse)
                .collect(Collectors.toList());
    }

}