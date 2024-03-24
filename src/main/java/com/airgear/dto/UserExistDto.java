package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExistDto {
    private String username;
    private boolean exist;
}
