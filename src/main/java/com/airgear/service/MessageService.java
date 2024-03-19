package com.airgear.service;

import com.airgear.model.message.request.ChangeTextRequest;
import com.airgear.model.message.request.SaveMessageRequest;
import com.airgear.model.message.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing messages.
 * Defines methods for handling various operations related to messages.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface MessageService {

    Page<MessageResponse> getAllMessageByGoodsId(Pageable pageable, long goodsId);

    Optional<MessageResponse> getMessageById(UUID messageId);

    MessageResponse create(SaveMessageRequest request);

    MessageResponse changeTextMessage(UUID messageId, ChangeTextRequest request);

    void deleteMessageById(UUID messageId);

    Object getTotalNumberOfSendMessages();
}
