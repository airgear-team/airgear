package com.airgear.repository;

import com.airgear.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET account_status_id = :accountStatusId WHERE id = :userId", nativeQuery = true)
    int setAccountStatusId(@Param("accountStatusId") long accountStatusId, @Param("userId") long userId);

    int countByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}