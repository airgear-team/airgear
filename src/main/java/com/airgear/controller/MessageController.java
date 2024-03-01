package com.airgear.controller;

import com.airgear.model.message.request.SaveMessageRequest;
import com.airgear.model.message.response.MessageResponse;
import com.airgear.service.MessageService;
import com.airgear.utils.Routes;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

import static com.airgear.exception.MessageExceptions.messageNotFound;

@RestController
@RequestMapping(Routes.MESSAGE)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(
            value = "/{goodsId}/byGoods",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<MessageResponse> views(@Parameter(hidden = true) Pageable pageable,
                                       @PathVariable long goodsId) {
        return messageService.getAllMessageByGoodsId(pageable, goodsId);
    }

    @GetMapping(
            value = "/{messageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public MessageResponse getMessageById(@PathVariable UUID messageId) {
        return messageService.getMessageById(messageId)
                .orElseThrow(() -> messageNotFound(messageId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody SaveMessageRequest request,
                                                  UriComponentsBuilder ucb) {
        MessageResponse response = messageService.create(request);
        return ResponseEntity
                .created(ucb.path("/{id}").build(response.id()))
                .body(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public void deleteMessageById(@PathVariable UUID messageId) {
        messageService.deleteMessageById(messageId);
    }
}
