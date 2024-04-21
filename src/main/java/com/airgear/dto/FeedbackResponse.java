package com.airgear.dto;

import com.airgear.model.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackResponse {

    private Long id;

    private Long userId;

    private String title;

    private String message;

    private OffsetDateTime createdAt;

    private FeedbackType feedbackType;

}
