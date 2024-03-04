package com.airgear.service;

import com.airgear.dto.FeedbackDTO;
import com.airgear.model.Feedback;

import javax.validation.Valid;
import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedback();

    Feedback getFeedbackById(Long id);

    public Feedback createFeedback(@Valid FeedbackDTO feedbackDTO);

    public void deleteFeedback(Long id);
}
