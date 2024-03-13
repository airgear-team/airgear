package com.airgear.mapper;

import com.airgear.dto.AccountStatusDto;
import com.airgear.model.AccountStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountStatusMapper {
    AccountStatusDto toDto(AccountStatus accountStatus);

    AccountStatus toAccountStatus(AccountStatusDto dto);
}
