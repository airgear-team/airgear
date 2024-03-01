package com.airgear.model.message.response;

import com.airgear.model.message.Message;

import java.util.UUID;

public record MessageResponse(UUID id,
                              String text) {

    public static MessageResponse fromMessage(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getText()
        );
    }
}
