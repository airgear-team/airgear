package com.airgear.model.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
    private String subject;
    private String message;
}