package com.airgear.dto;

import com.airgear.entity.CustomEmailMessage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomEmailMessageDto {
    private String recipient;
    private String subject;
    private String text;
    private String attachment;

    public CustomEmailMessage toCustomEmailMessage() {
        return CustomEmailMessage.builder()
                .recipient(recipient)
                .subject(subject)
                .text(text)
                .attachment(attachment)
                .build();
    }

    public static CustomEmailMessageDto fromCustomEmailMessageDto(CustomEmailMessage emailMessage) {
        return CustomEmailMessageDto.builder()
                .recipient(emailMessage.getRecipient())
                .subject(emailMessage.getSubject())
                .text(emailMessage.getText())
                .attachment(emailMessage.getAttachment())
                .build();
    }
}
