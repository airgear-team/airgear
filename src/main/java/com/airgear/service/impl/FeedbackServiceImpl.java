package com.airgear.service.impl;

import com.airgear.dto.FeedbackResponse;
import com.airgear.dto.FeedbackSaveRequest;
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
    public FeedbackResponse create(String email, FeedbackSaveRequest request) {
        User user = getUser(email);
        return feedbackMapper.toDto(save(request, user));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackResponse> list(Pageable pageable) {
        return feedbackRepository.findAll(pageable)
                .map(feedbackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackResponse> getById(long id) {
        return feedbackRepository.findById(id)
                .map(feedbackMapper::toDto);
    }

    @Override
    public void deleteFeedback(long id) {
        if (!feedbackRepository.existsById(id)) throw FeedbackExceptions.feedbackNotFound(id);
        feedbackRepository.deleteById(id);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserExceptions.userNotFound(email));
    }

    private Feedback save(FeedbackSaveRequest request, User user) {
        Feedback feedback = feedbackMapper.toModel(request);
        feedback.setUser(user);
        feedback.setCreatedAt(OffsetDateTime.now());
        return feedbackRepository.save(feedback);
    }
}
