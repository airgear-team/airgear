package com.airgear.service.impl;

import com.airgear.model.User;
import com.airgear.model.email.EmailMessage;
import com.airgear.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromMail;
    // TODO custom Exceptions
    // TODO refactoring the void sendWelcomeEmail(User user) method
    private final JavaMailSender mailSender;

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

    public void sendWelcomeEmail(User user) {
        String recipientAddress = user.getEmail();
        String username = user.getName();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientAddress);
            helper.setSubject("Ласкаво просимо до нашого сервісу, " + username + "!");

            String htmlContent = "<html><body>"
                    + "<h1>Ласкаво просимо до нашого сервісу, " + username + "!</h1>"
                    + "<p>Дякуємо за реєстрацію на нашому сервісі. Ми раді, що ви обрали нас.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("An error occurred while sending the email", e);
        }
    }
}
