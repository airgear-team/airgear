package com.airgear.service.impl;

import com.airgear.dto.FeedbackDto;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.FeedbackMapper;
import com.airgear.model.Feedback;
import com.airgear.model.User;
import com.airgear.repository.FeedbackRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "feedbackService")
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;


    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback createFeedback(String email, FeedbackDto feedbackDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> UserExceptions.userNotFound(email));
        Feedback feedback = feedbackMapper.toModel(feedbackDTO);
        feedback.setUser(user);
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}

