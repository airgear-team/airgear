package com.airgear.mapper;

import com.airgear.dto.FeedbackCreateRequest;
import com.airgear.dto.FeedbackCreateResponse;
import com.airgear.dto.FeedbackGetResponse;
import com.airgear.model.Feedback;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface FeedbackMapper {
    FeedbackCreateResponse toCreateResponse(Feedback feedback);
    FeedbackGetResponse toGetResponse(Feedback feedback);
    List<FeedbackGetResponse> toGetResponseList(List<Feedback> feedback);

    Feedback toModel(FeedbackCreateRequest dto);
}
