package com.airgear.service.impl;

import com.airgear.dto.MessageDto;
import com.airgear.mapper.MessageMapper;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.model.Message;
import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.SaveMessageRequestDTO;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.MessageRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.airgear.exception.GoodsExceptions.goodsNotFound;
import static com.airgear.exception.MessageExceptions.messageNotFound;
import static com.airgear.exception.UserExceptions.userNotFound;

@Service
@Transactional
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public Page<MessageDto> getAllMessageByGoodsId(Pageable pageable, long goodsId) {
        return messageRepository.findAllByGoods_Id(pageable, goodsId)
                .map(messageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageDto> getMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(messageMapper::toDto);
    }

    @Override
    public MessageDto create(SaveMessageRequestDTO request) {
        Goods goods = getGoods(request);
        User user = getUser(request);
        UUID uuid = UUID.randomUUID();

        return save(request, goods, user, uuid);
    }

    @Override
    public MessageDto changeTextMessage(UUID messageId, ChangeTextRequestDTO request) {
        Message message = getMessage(messageId);
        message.setText(request.getText());
        return messageMapper.toDto(message);
    }

    private Message getMessage(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> messageNotFound(messageId));
    }

    @Override
    public void deleteMessageById(UUID messageId) {
        if (!messageRepository.existsById(messageId)) throw messageNotFound(messageId);
        messageRepository.deleteById(messageId);
    }

    @Override
    public Long getTotalNumberOfSendMessages() {
        return messageRepository.count();
    }

    private MessageDto save(SaveMessageRequestDTO request, Goods goods, User user, UUID uuid) {
        Message message = new Message();
        message.setId(uuid);
        message.setText(request.getText());
        message.setGoods(goods);
        message.setUser(user);
        message.setSendAt(OffsetDateTime.now());

        return messageMapper.toDto(messageRepository.save(message));
    }

    private User getUser(SaveMessageRequestDTO request) {
        return userRepository.findById(request.getUserId())
                .orElseThrow(() -> userNotFound(request.getUserId()));
    }

    private Goods getGoods(SaveMessageRequestDTO request) {
        return goodsRepository.findById(request.getGoodsId())
                .orElseThrow(() -> goodsNotFound(request.getGoodsId()));
    }
}
