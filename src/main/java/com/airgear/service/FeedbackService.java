package com.airgear.service;

import com.airgear.dto.FeedbackDto;
import com.airgear.model.Feedback;

import javax.validation.Valid;
import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedback();

    Feedback getFeedbackById(Long id);

    Feedback createFeedback(@Valid FeedbackDto feedbackDTO);

    void deleteFeedback(Long id);
}
