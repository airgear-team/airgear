package com.airgear.service;

import com.airgear.dto.FeedbackResponse;
import com.airgear.dto.FeedbackSaveRequest;
import com.airgear.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    FeedbackResponse create(String email, FeedbackSaveRequest request);

    Page<FeedbackResponse> list(Pageable pageable);

    Optional<FeedbackResponse> getById(long id);

    void deleteFeedback(long id);

}
