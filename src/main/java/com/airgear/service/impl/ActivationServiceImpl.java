package com.airgear.service.impl;

import com.airgear.entity.EmailMessage;
import com.airgear.model.User;
import com.airgear.model.UserStatus;
import com.airgear.repository.UserRepository;
import com.airgear.service.ActivationService;
import com.airgear.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final EmailService emailService;

    public void sendActivationEmail(User user) {
        String token = user.getActivationToken();
        String activationLink = baseUrl + "/auth/activate?token=" + token;
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("Account Activation");
        emailMessage.setMessage("Click the link to activate your account: " + activationLink);
        emailService.sendMail(emailMessage, Set.of(user.getEmail()));
    }

    public boolean activateUser(String token) {
        User user = userRepository.findByActivationToken(token);
        if (user != null && user.getStatus() == UserStatus.PENDING_ACTIVATION) {
            user.setStatus(UserStatus.ACTIVE);
            user.setActivationToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}