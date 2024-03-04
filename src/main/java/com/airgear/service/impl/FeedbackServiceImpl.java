package com.airgear.service.impl;

import com.airgear.dto.FeedbackDTO;
import com.airgear.model.Feedback;
import com.airgear.model.User;
import com.airgear.repository.FeedbackRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback createFeedback(FeedbackDTO feedbackDTO) {
        User user = userRepository.findById(feedbackDTO.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User with given ID not found"));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setMessage(feedbackDTO.getMessage());
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}

