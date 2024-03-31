package com.airgear.controller;

import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.MessageDto;
import com.airgear.dto.SaveMessageRequestDTO;
import com.airgear.service.MessageService;
import com.airgear.utils.Routes;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping(
            value = "/{goodsId}/goodsId",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<MessageDto> views(@Parameter(hidden = true) Pageable pageable,
                                  @PathVariable long goodsId) {
        return messageService.getAllMessageByGoodsId(pageable, goodsId);
    }

    @GetMapping(
            value = "/{messageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public MessageDto getMessageById(@PathVariable UUID messageId) {
        return messageService.getMessageById(messageId)
                .orElseThrow(() -> messageNotFound(messageId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public ResponseEntity<MessageDto> create(@RequestBody @Valid SaveMessageRequestDTO request,
                                             UriComponentsBuilder ucb) {
        MessageDto response = messageService.create(request);
        return ResponseEntity
                .created(ucb.path("/{id}").build(response.getId()))
                .body(response);
    }

    @PatchMapping(
            value = "/{messageId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public MessageDto changeText(@PathVariable UUID messageId,
                                 @RequestBody @Valid ChangeTextRequestDTO request) {
        return messageService.changeTextMessage(messageId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public void deleteMessageById(@PathVariable UUID messageId) {
        messageService.deleteMessageById(messageId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/total-number")
    public ResponseEntity<Long> totalNumberOfSendMessages() {
        return ResponseEntity.ok(messageService.getTotalNumberOfSendMessages());
    }
}
