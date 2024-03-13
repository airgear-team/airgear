package com.airgear.mapper;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.UserReview;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = UserReviewMapper.class)
public interface UserReviewSetMapper {
    Set<UserReviewDto> toDtoSet(Set<UserReview> userReviews);

    Set<UserReview> toUserReviewSet(Set<UserReviewDto> dtos);
}
