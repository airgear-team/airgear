package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExistResponse {
    private String username;
    private Boolean exist;
}
