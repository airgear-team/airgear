package com.airgear.service;

import com.airgear.model.email.EmailMessage;

public interface EmailService {
    void sendMail(String recipientAddress , EmailMessage emailMessage);
}
