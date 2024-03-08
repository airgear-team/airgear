package com.airgear.repository;

import com.airgear.model.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for managing {@link Message} entities.
 * Extends the Spring Data {@link JpaRepository} with specific methods for handling messages.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findAllByGoods_Id(Pageable pageable, Long goodsId);

}
