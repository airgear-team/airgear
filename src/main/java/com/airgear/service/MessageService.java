package com.airgear.service;

import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.MessageDto;
import com.airgear.dto.SaveMessageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    Page<MessageDto> getAllMessageByGoodsId(Pageable pageable, long goodsId);

    Optional<MessageDto> getMessageById(UUID messageId);

    MessageDto create(SaveMessageRequestDTO request);

    MessageDto changeTextMessage(UUID messageId, ChangeTextRequestDTO request);

    void deleteMessageById(UUID messageId);

    Long getTotalNumberOfSendMessages();
}
