package com.airgear.repository;

import com.airgear.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

    AccountStatus findByStatusName(String statusName);
}
