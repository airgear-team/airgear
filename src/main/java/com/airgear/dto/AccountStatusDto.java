package com.airgear.dto;

import com.airgear.model.AccountStatus;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public AccountStatus toAccountStatus() {
        return AccountStatus.builder()
                .id(id)
                .statusName(statusName)
                .description(description)
                .build();
    }

    public static AccountStatusDto fromAccountStatus(AccountStatus accountStatus) {
        return AccountStatusDto.builder()
                .id(accountStatus.getId())
                .statusName(accountStatus.getStatusName())
                .description(accountStatus.getDescription())
                .build();
    }

    public static List<AccountStatusDto> fromAccountStatuses(List<AccountStatus> accountStatuses) {
        List<AccountStatusDto> result = new ArrayList<>();
        accountStatuses.forEach(accountStatus -> result.add(AccountStatusDto.fromAccountStatus(accountStatus)));
        return result;
    }

}
