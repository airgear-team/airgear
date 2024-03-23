package com.airgear.controller;

/*
 * An example of a json request structure in Postman.
 * {
 * "addresses": ["test1@gmail.com", "test2@gmail.com"],
 * "emailMessage": {
 * "subject": "Test",
 * "message": "This is tested email message."
 * }
 * }
 */

import com.airgear.model.email.EmailsRequestStructure;
import com.airgear.service.impl.EmailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class EmailController {

    private final EmailServiceImpl emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailsRequestStructure request) {
        return emailService.sendMail(request.getEmailMessage(), request.getAddresses());
    }
}
