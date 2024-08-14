package com.airgear.service;

import com.airgear.model.User;

public interface ActivationService {
    void sendActivationEmail(User user);

    boolean activateUser(String token);
}
