package com.airgear.controller;

/**
 *  An example of a json request structure in Postman.
 *  {
 *     "addresses": ["test1@gmail.com", "test2@gmail.com"],
 *     "emailMessage": {
 *         "subject": "Test",
 *         "message": "This is test email message."
 *     }
 *  }
 */

import com.airgear.model.email.EmailsRequestStructure;
import com.airgear.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailsRequestStructure request) {
        try {
            for (String address: request.getAddresses()) {
                emailService.sendMail(address, request.getEmailMessage());
                System.out.println("The email was sent successfully.");
            }

            return "All emails were sent successfully.";
        } catch (Exception e) {
            throw new RuntimeException("Unable to send email.");
        }
    }
}
