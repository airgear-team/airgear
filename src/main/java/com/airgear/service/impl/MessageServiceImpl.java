package com.airgear.service.impl;

import com.airgear.exception.MessageExceptions;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.model.message.Message;
import com.airgear.model.message.request.ChangeTextRequest;
import com.airgear.model.message.request.SaveMessageRequest;
import com.airgear.model.message.response.MessageResponse;
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
    public Page<MessageResponse> getAllMessageByGoodsId(Pageable pageable, long goodsId) {
        return messageRepository.findAllByGoods_Id(pageable, goodsId)
                .map(MessageResponse::fromMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageResponse> getMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(MessageResponse::fromMessage);
    }

    @Override
    public MessageResponse create(SaveMessageRequest request) {
        Goods goods = getGoods(request);
        User user = getUser(request);
        UUID uuid = UUID.randomUUID();

        return save(request, goods, user, uuid);
    }

    @Override
    public MessageResponse changeTextMessage(UUID messageId, ChangeTextRequest request) {
        Message message = getMessage(messageId);
        message.setText(request.text());
        return MessageResponse.fromMessage(message);
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

    private MessageResponse save(SaveMessageRequest request, Goods goods, User user, UUID uuid) {
        Message message = new Message();
        message.setId(uuid);
        message.setText(request.text());
        message.setGoods(goods);
        message.setUser(user);

        return MessageResponse.fromMessage(messageRepository.save(message));
    }

    private User getUser(SaveMessageRequest request) {
        return userRepository.findById(request.userId())
                .orElseThrow(() -> userNotFound(request.userId()));
    }

    private Goods getGoods(SaveMessageRequest request) {
        return goodsRepository.findById(request.goodsId())
                .orElseThrow(() -> goodsNotFound(request.goodsId()));
    }
}
