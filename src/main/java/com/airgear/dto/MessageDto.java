package com.airgear.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class MessageDto {
    UUID id;
    String text;
}
