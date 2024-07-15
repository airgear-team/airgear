package com.airgear.service.impl;

import com.airgear.dto.CustomEmailMessageDto;
import com.airgear.dto.UserGetResponse;
import com.airgear.entity.CustomEmailMessage;
import com.airgear.entity.EmailMessage;
import com.airgear.exception.EmailExceptions;
import com.airgear.repository.EmailMessageRepository;
import com.airgear.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
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
    JavaMailSender mailSender;
    private final EmailMessageRepository emailMessageRepository;

    public EmailServiceImpl(JavaMailSender mailSender, EmailMessageRepository emailMessageRepository) {
        this.mailSender = mailSender;
        this.emailMessageRepository = emailMessageRepository;
    }

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
                    } catch (RuntimeException e) {
                        log.error("Unable to send email to address: {}", address, e);
                        throw  EmailExceptions.unableToSendEmail(address);
                        //TODO save unsent email
                    }
                }, counter.getAndIncrement(), TimeUnit.SECONDS);
            }
            return "All emails were submitted for sending.";
        } catch (RuntimeException e) {
            log.error("Unable to submit emails for sending.", e);
            //TODO save unsent emails
            throw  EmailExceptions.unableToSendEmail(addresses.toString());
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public String sendCustomEmail(CustomEmailMessageDto request) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(request.getRecipient());
            helper.setSubject(request.getSubject());
            helper.setText(request.getText());
            FileSystemResource fileSystem = new FileSystemResource(new File(request.getAttachment()));
            helper.addAttachment(fileSystem.getFilename(), fileSystem);
            mailSender.send(message);

            log.info(this.save(request));
            return "The email was sent successfully.";
        } catch (MailSendException | NullPointerException | MessagingException e) {
            log.error("Unable to submit this email. ", e);
            throw  EmailExceptions.unableToSendEmail(request.getRecipient());
        }
    }


    @Override
    public String save(CustomEmailMessageDto message) {
        CustomEmailMessage newMessage = message.toCustomEmailMessage();
        try {
            emailMessageRepository.save(newMessage);
            return "The email was save successfully.";
        } catch (RuntimeException e) {
            log.error("Unable to save this email. ", e);
            throw EmailExceptions.unableToSaveEmail(message.getRecipient());
        }
    }

    public String sendWelcomeEmail(UserGetResponse user) {
        String recipientAddress = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientAddress);
            helper.setSubject("Ласкаво просимо до нашого сервісу, " + recipientAddress + "!");

            String htmlContent = "<html><body>"
                    + "<h1>Ласкаво просимо до нашого сервісу, " + recipientAddress + "!</h1>"
                    + "<p>Дякуємо за реєстрацію на нашому сервісі. Ми раді, що ви обрали нас.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);

            CustomEmailMessageDto savingMessage = CustomEmailMessageDto.builder()
                    .recipient(recipientAddress)
                    .subject("Ласкаво просимо до нашого сервісу, " + recipientAddress + "!")
                    .text(htmlContent)
                    .build();

            log.info(this.save(savingMessage));
            return "The welcome email was send successfully.";
        } catch (MailSendException | NullPointerException | MessagingException e) {
            e.printStackTrace();
            throw  EmailExceptions.unableToSendEmail(user.getEmail());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomEmailMessage> filterByEmail(String email) {
        return emailMessageRepository.findAllByRecipient(email)
                .filter(msgList -> !msgList.isEmpty())
                .orElseThrow(EmailExceptions::notFoundEmails);
    }

    @Override
    public Page<CustomEmailMessage> filterByEmailWithPagination(String email, int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        List<CustomEmailMessage> allFilteredMessages = filterByEmail(email);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allFilteredMessages.size());
        if (start > end) {
            throw EmailExceptions.pageNotFound();
        }

        try {
            List<CustomEmailMessage> pageContent = allFilteredMessages.subList(start, end);
            return new PageImpl<>(pageContent, pageRequest, allFilteredMessages.size());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw EmailExceptions.notFoundEmails();
        }
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

}
