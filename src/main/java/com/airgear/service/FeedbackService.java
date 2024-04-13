package com.airgear.service;

import com.airgear.dto.FeedbackDto;
import com.airgear.model.Feedback;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedback();

    Feedback getFeedbackById(Long id);

    Feedback createFeedback(String email, @Valid FeedbackDto feedbackDTO);

    void deleteFeedback(Long id);
}
