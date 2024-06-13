package com.airgear.service;

import com.airgear.dto.CustomEmailMessageDto;
import com.airgear.dto.UserGetResponse;
import com.airgear.entity.CustomEmailMessage;
import com.airgear.entity.EmailMessage;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EmailService {
    String sendMail(EmailMessage emailMessage, Set<String> addresses);

    String  sendWelcomeEmail(UserGetResponse user);


    String sendCustomEmail(CustomEmailMessageDto request);

    String  save(CustomEmailMessageDto message);

    List<CustomEmailMessage> filterByEmail(String email);

    Page<CustomEmailMessage> filterByEmailWithPagination(String  email, int page, int size);
}
