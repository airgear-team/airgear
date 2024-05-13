package com.airgear.dto;

import com.airgear.model.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCreateRequest {
    @NotBlank
    @Size(max = 255, message = "The size of the title should not exceed 255 characters")
    private String title;

    @NotBlank
    @Size(max = 1000, message = "The size of the message should not exceed 1000 characters")
    private String message;

    @NotNull
    private FeedbackType feedbackType;
}
