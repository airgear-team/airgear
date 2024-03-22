package com.airgear.service.impl;

import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.model.Message;
import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.SaveMessageRequestDTO;
import com.airgear.dto.MessageResponseDTO;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.MessageRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.airgear.exception.GoodsExceptions.goodsNotFound;
import static com.airgear.exception.MessageExceptions.messageNotFound;
import static com.airgear.exception.UserExceptions.userNotFound;

/**
 * Implementation of the {@link MessageService} interface responsible
 * for handling message-related operations.
 * <p>
 *
 * @author Oleksandr Tuleninov, Vitalii Shkaraputa
 * @version 01
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              GoodsRepository goodsRepository,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<MessageResponseDTO> getAllMessageByGoodsId(Pageable pageable, long goodsId) {
        return messageRepository.findAllByGoods_Id(pageable, goodsId)
                .map(MessageResponseDTO::fromMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageResponseDTO> getMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(MessageResponseDTO::fromMessage);
    }

    @Override
    public MessageResponseDTO create(SaveMessageRequestDTO request) {
        Goods goods = getGoods(request);
        User user = getUser(request);
        UUID uuid = UUID.randomUUID();

        return save(request, goods, user, uuid);
    }

    @Override
    public MessageResponseDTO changeTextMessage(UUID messageId, ChangeTextRequestDTO request) {
        Message message = getMessage(messageId);
        message.setText(request.getText());
        return MessageResponseDTO.fromMessage(message);
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

    private MessageResponseDTO save(SaveMessageRequestDTO request, Goods goods, User user, UUID uuid) {
        Message message = new Message();
        message.setId(uuid);
        message.setText(request.getText());
        message.setGoods(goods);
        message.setUser(user);

        return MessageResponseDTO.fromMessage(messageRepository.save(message));
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
