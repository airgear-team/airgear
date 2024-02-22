package com.airgear.repository;

import com.airgear.model.AccountStatus;
import com.airgear.model.enums.AccountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

    AccountStatus findByStatusName(Enum<AccountStatusEnum> statusName);
}
