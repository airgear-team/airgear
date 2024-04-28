package com.airgear.dto;

import com.airgear.model.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCreateResponse {

    private Long id;
    private String title;
    private String message;
    private FeedbackType feedbackType;
}
