package com.airgear.dto;

import com.airgear.model.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackGetResponse {

    private String title;
    private String message;
    private FeedbackType feedbackType;
}
