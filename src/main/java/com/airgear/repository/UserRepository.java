package com.airgear.repository;

import com.airgear.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);


    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET account_status_id = ? where id = ?", nativeQuery = true)
    void setAccountStatusId(@Param("id") long userId, @Param("account_status_id") long accountStatusId);
}