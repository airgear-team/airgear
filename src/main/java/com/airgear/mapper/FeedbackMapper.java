package com.airgear.mapper;

import com.airgear.dto.FeedbackResponse;
import com.airgear.dto.FeedbackSaveRequest;
import com.airgear.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface FeedbackMapper {

    @Mapping(target = "userId", source = "user.id")
    FeedbackResponse toDto(Feedback feedback);

    Feedback toModel(FeedbackSaveRequest dto);

}
