package com.airgear.mapper;

import com.airgear.dto.*;
import com.airgear.model.UserReview;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {
    UserReviewCreateResponse toCreateResponse(UserReview userReview);
    UserReviewUpdateResponse toUpdateResponse(UserReview userReview);
    UserReviewGetResponse toGetResponse(UserReview userReview);

    UserReview toModel(UserReviewGetRequest dto);
    UserReview toModel(UserReviewCreateRequest dto);
    UserReview toModel(UserReviewUpdateRequest dto);
    Set<UserReviewCreateRequest> toDtoSet(Set<UserReview> userReviews);

    Set<UserReview> toModelSet(Set<UserReviewCreateRequest> dtos);
}
