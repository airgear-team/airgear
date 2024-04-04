package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStatusDto {
    private Long id;
    private String statusName;
    private String description;
}
