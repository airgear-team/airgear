package com.airgear.controller;

import com.airgear.dto.CustomEmailMessageDto;
import com.airgear.dto.UserGetResponse;
import com.airgear.entity.CustomEmailMessage;
import com.airgear.entity.EmailsRequestStructure;
import com.airgear.service.impl.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mail")
public class EmailController {

    private final EmailServiceImpl emailService;

    @Autowired
    public EmailController(EmailServiceImpl emailService){
        this.emailService=emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailsRequestStructure request) {
        return emailService.sendMail(request.getEmailMessage(), request.getAddresses());
    }

    @PostMapping("/custom-message")
    public String sendCustomEmail(@RequestBody CustomEmailMessageDto request) {
        return emailService.sendCustomEmail(request);
    }

    @PostMapping("/welcome-message")
    public String sendWelcomeEmail(@RequestBody UserGetResponse user) {
        return emailService.sendWelcomeEmail(user);
    }

    @PostMapping("/save-message")
    public ResponseEntity<String> saveMessage(@RequestBody CustomEmailMessageDto message) {
        return ResponseEntity.ok(emailService.save(message));
    }

    @GetMapping("/filter-by-email/")
    public ResponseEntity<List<CustomEmailMessage>> filterByEmail(@RequestParam String email) {
        return ResponseEntity.ok(emailService.filterByEmail(email));
    }

    @GetMapping("/filter-with-pagination")
    public ResponseEntity<Page<CustomEmailMessage>> filterWithPagination(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<CustomEmailMessage> messagePage = emailService.filterByEmailWithPagination(email, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(messagePage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(messagePage.getSize()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(messagePage);
    }
}
