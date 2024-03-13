package com.airgear.mapper;

import com.airgear.dto.FeedbackDto;
import com.airgear.model.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface FeedbackMapper {
    FeedbackDto toDto(Feedback feedback);

    Feedback toFeedback(FeedbackDto dto);
}
