package com.airgear.repository;

import com.airgear.entity.CustomEmailMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmailMessageRepository extends CrudRepository<CustomEmailMessage, Long> {
    Optional<List<CustomEmailMessage>> findAllByRecipient(String recipient);
}
