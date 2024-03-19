package com.airgear.dto;

import com.airgear.model.message.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a response object for messages with specific fields.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class MessageResponseDTO {
    UUID id;
    String text;

    /**
     * Creates a new {@code MessageResponse} instance based on the provided {@code Message} entity.
     *
     * @param message The source message entity.
     * @return A new {@code MessageResponse} instance with data from the given message.
     */
    public static MessageResponseDTO fromMessage(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getText()
        );
    }
}
