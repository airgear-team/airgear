package com.airgear.service.impl;

import com.airgear.dto.FeedbackCreateRequest;
import com.airgear.dto.FeedbackCreateResponse;
import com.airgear.dto.FeedbackGetResponse;
import com.airgear.exception.FeedbackExceptions;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.FeedbackMapper;
import com.airgear.model.Feedback;
import com.airgear.model.User;
import com.airgear.repository.FeedbackRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackCreateResponse create(String email, FeedbackCreateRequest request) {
        User user = getUser(email);
        return feedbackMapper.toCreateResponse(save(request, user));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackGetResponse> list(Pageable pageable) {
        return feedbackRepository.findAll(pageable)
                .map(feedbackMapper::toGetResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackGetResponse> getById(Long id) {
        return feedbackRepository.findById(id)
                .map(feedbackMapper::toGetResponse);
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) throw FeedbackExceptions.feedbackNotFound(id);
        feedbackRepository.deleteById(id);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserExceptions.userNotFound(email));
    }

    private Feedback save(FeedbackCreateRequest request, User user) {
        Feedback feedback = feedbackMapper.toModel(request);
        feedback.setUser(user);
        feedback.setCreatedAt(OffsetDateTime.now());
        return feedbackRepository.save(feedback);
    }
}
