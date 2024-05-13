package com.airgear.service;

import com.airgear.dto.FeedbackCreateRequest;
import com.airgear.dto.FeedbackCreateResponse;
import com.airgear.dto.FeedbackGetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeedbackService {

    FeedbackCreateResponse create(String email, FeedbackCreateRequest request);

    Page<FeedbackGetResponse> list(Pageable pageable);

    Optional<FeedbackGetResponse> getById(Long id);

    void deleteFeedback(Long id);
}
