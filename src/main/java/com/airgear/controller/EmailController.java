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


@Slf4j
@RestController
@RequestMapping("/mail")
public class EmailController {

    private EmailServiceImpl emailService;

    @Autowired
    public EmailController(EmailServiceImpl emailService){
        this.emailService=emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailsRequestStructure request) {
        return emailService.sendMail(request.getEmailMessage(), request.getAddresses());
    }
}
