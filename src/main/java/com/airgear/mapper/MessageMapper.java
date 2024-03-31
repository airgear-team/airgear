package com.airgear.mapper;

import com.airgear.dto.MessageDto;
import com.airgear.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto toDto(Message message);
}