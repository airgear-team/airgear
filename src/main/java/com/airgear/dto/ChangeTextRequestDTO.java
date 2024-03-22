package com.airgear.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Represents a request object for changing the text of a message with specific fields.
 *
 * @author Vitalii Shkaraputa
 * @version 01
 */

@Data
@Setter(AccessLevel.NONE)
public class ChangeTextRequestDTO {

        @NotBlank(message = "text must not be empty")
        String text;
}