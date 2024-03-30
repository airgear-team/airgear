package com.airgear.dto;

import com.airgear.model.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class MessageResponseDTO {
    UUID id;
    String text;

    public static MessageResponseDTO fromMessage(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getText()
        );
    }
}
