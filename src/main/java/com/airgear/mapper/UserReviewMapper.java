package com.airgear.mapper;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.UserReview;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {
    UserReviewDto toDto(UserReview userReview);

    UserReview toModel(UserReviewDto dto);

    Set<UserReviewDto> toDtoSet(Set<UserReview> userReviews);

    Set<UserReview> toModelSet(Set<UserReviewDto> dtos);
}
