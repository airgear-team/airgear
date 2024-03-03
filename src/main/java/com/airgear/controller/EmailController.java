package com.airgear.controller;

/**
 * An example of a json request structure in Postman.
 * {
 * "addresses": ["test1@gmail.com", "test2@gmail.com"],
 * "emailMessage": {
 * "subject": "Test",
 * "message": "This is test email message."
 * }
 * }
 */

import com.airgear.model.email.EmailsRequestStructure;
import com.airgear.service.impl.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailsRequestStructure request) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger dailyCounter = new AtomicInteger(0);
        try {
            for (String address : request.getAddresses()) {
                if (dailyCounter.get() >= 1500) {
                    //TODO save unsent emails
                    return "Limit emails per day.";
                }
                executorService.schedule(() -> {
                    try {
                        emailService.sendMail(address, request.getEmailMessage());
                        log.info("The email was sent successfully to address: {}", address);
                    } catch (Exception e) {
                        log.error("Unable to send email to address: {}", address, e);
                    }
                }, counter.getAndIncrement(), TimeUnit.SECONDS);
            }
            return "All emails were submitted for sending.";
        } catch (Exception e) {
            log.error("Unable to submit emails for sending.", e);
            throw new RuntimeException("Unable to send emails.");
        } finally {
            executorService.shutdown();
        }
    }
}
