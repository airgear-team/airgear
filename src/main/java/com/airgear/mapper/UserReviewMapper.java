package com.airgear.mapper;

import com.airgear.dto.UserReviewDto;
import com.airgear.model.UserReview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {
    UserReviewDto toDto(UserReview userReview);

    UserReview toUserReview(UserReviewDto dto);
}
