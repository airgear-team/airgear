package com.airgear.service;

import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.SaveMessageRequestDTO;
import com.airgear.dto.MessageResponseDTO;
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

    Page<MessageResponseDTO> getAllMessageByGoodsId(Pageable pageable, long goodsId);

    Optional<MessageResponseDTO> getMessageById(UUID messageId);

    MessageResponseDTO create(SaveMessageRequestDTO request);

    MessageResponseDTO changeTextMessage(UUID messageId, ChangeTextRequestDTO request);

    void deleteMessageById(UUID messageId);

}
