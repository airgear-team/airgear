package com.airgear.model.message.request;

import javax.validation.constraints.NotBlank;

/**
 * Represents a request object for changing the text of a message with specific fields.
 * This class is designed as a record, providing immutability and a concise syntax.
 * <p>
 *
 * @author Vitalii Shkaraputa
 * @version 01
 */
public record ChangeTextRequest(

        @NotBlank(message = "text must not be empty")
        String text

) {
}
