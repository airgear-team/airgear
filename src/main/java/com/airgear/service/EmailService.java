package com.airgear.service;

import com.airgear.model.email.EmailMessage;

import java.util.Set;

public interface EmailService {
    String sendMail(EmailMessage emailMessage, Set<String> addresses);
}
