package com.airgear.controller;

import com.airgear.dto.ChangeTextRequestDTO;
import com.airgear.dto.SaveMessageRequestDTO;
import com.airgear.dto.MessageResponseDTO;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

import static com.airgear.exception.MessageExceptions.messageNotFound;

/**
 * Controller class for managing messages.
 * Handles HTTP requests related to messages, such as retrieval, creation, updating, and deletion.
 * <p>
 *
 * @author Oleksandr Tuleninov, Vitalii Shkaraputa
 * @version 01
 */
@RestController
@RequestMapping(Routes.MESSAGE)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Retrieves a paginated list of messages associated with a specific goods ID.
     *
     * @param pageable Pageable object for pagination configuration.
     * @param goodsId  The ID of the goods for which messages are retrieved.
     * @return A paginated list of MessageResponse objects.
     */
    @GetMapping(
            value = "/{goodsId}/goodsId",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<MessageResponseDTO> views(@Parameter(hidden = true) Pageable pageable,
                                          @PathVariable long goodsId) {
        return messageService.getAllMessageByGoodsId(pageable, goodsId);
    }

    /**
     * Retrieves a single message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The MessageResponse object representing the retrieved message.
     * @throws ResponseStatusException If no message is found with the given ID.
     */
    @GetMapping(
            value = "/{messageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public MessageResponseDTO getMessageById(@PathVariable UUID messageId) {
        return messageService.getMessageById(messageId)
                .orElseThrow(() -> messageNotFound(messageId));
    }

    /**
     * Creates a new message.
     *
     * @param request          The SaveMessageRequest containing the data for creating a new message.
     * @param ucb              UriComponentsBuilder for building the response URI.
     * @return ResponseEntity with a created MessageResponse and a link to the created resource.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public ResponseEntity<MessageResponseDTO> create(@RequestBody @Valid SaveMessageRequestDTO request,
                                                     UriComponentsBuilder ucb) {
        MessageResponseDTO response = messageService.create(request);
        return ResponseEntity
                .created(ucb.path("/{id}").build(response.getId()))
                .body(response);
    }

    /**
     * Updates the text of a message.
     *
     * @param messageId The ID of the message to update.
     * @param request   The ChangeTextRequest containing the new text for the message.
     * @return The updated MessageResponse object.
     */
    @PatchMapping(
            value = "/{messageId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public MessageResponseDTO changeText(@PathVariable UUID messageId,
                                         @RequestBody @Valid ChangeTextRequestDTO request) {
        return messageService.changeTextMessage(messageId, request);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     */
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
