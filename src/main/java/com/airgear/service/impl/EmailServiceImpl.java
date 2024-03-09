package com.airgear.service.impl;

import com.airgear.model.email.EmailMessage;
import com.airgear.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendMail(EmailMessage emailMessage, Set<String> addresses) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger dailyCounter = new AtomicInteger(0);
        try {
            for (String address : addresses) {
                if (dailyCounter.get() >= 1500) {
                    //TODO save unsent emails
                    return "Limit emails per day.";
                }
                executorService.schedule(() -> {
                    try {
                        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                        simpleMailMessage.setFrom(fromMail);
                        simpleMailMessage.setTo(address);
                        simpleMailMessage.setSubject(emailMessage.getSubject());
                        simpleMailMessage.setText(emailMessage.getMessage());
                        mailSender.send(simpleMailMessage);
                        log.info("The email was sent successfully to address: {}", address);
                    } catch (Exception e) {
                        log.error("Unable to send email to address: {}", address, e);
                        //TODO save unsent email
                    }
                }, counter.getAndIncrement(), TimeUnit.SECONDS);
            }
            return "All emails were submitted for sending.";
        } catch (Exception e) {
            log.error("Unable to submit emails for sending.", e);
            //TODO save unsent emails
            throw new RuntimeException("Unable to send emails.");
        } finally {
            executorService.shutdown();
        }
    }


}
