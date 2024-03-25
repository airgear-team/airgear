package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

/**
 * AccountStatusDto class. Fields are similar to AccountStatus entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */

@Data
@Builder
public class AccountStatusDto {
    private Long id;
    private String statusName;
    private String description;
}
