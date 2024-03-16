package com.airgear.controller;

import com.airgear.dto.RentalCardDto;
import com.airgear.exception.ForbiddenException;
import com.airgear.model.RentalCard;
import com.airgear.model.User;
import com.airgear.service.RentalCardService;
import com.airgear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rental")
public class RentalCardController {
    @Autowired
    private RentalCardService rentalCardService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @RequestMapping(value = "/{rentalCardId}", method = RequestMethod.GET)
    public RentalCard getRentalCardById(@PathVariable Long rentalCardId) {
        return rentalCardService.getRentalCardById(rentalCardId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public RentalCard createRentalCard(Authentication auth, @RequestBody RentalCardDto rentalCardDto) {
        return rentalCardService.saveRentalCard(rentalCardDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PutMapping("/{rentalCardId}")
    public ResponseEntity<RentalCardDto> updateRentalCard(
            Authentication auth,
            @PathVariable Long rentalCardId,
            @Valid @RequestBody RentalCardDto updatedRentalCard) {
        User user = userService.findByUsername(auth.getName());
        RentalCard existingRentalCard = rentalCardService.getRentalCardById(rentalCardId);
        if (!(user.getId().equals(existingRentalCard.getRenter().getId())
                ||user.getId().equals(existingRentalCard.getLessor().getId())
                || user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN")))){
            throw new ForbiddenException("It is not your rental card");
        }
        return ResponseEntity.ok(updatedRentalCard);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{rentalCardId}")
    public ResponseEntity<String> deleteRentalCard(Authentication auth, @PathVariable Long rentalCardId){
        User user = userService.findByUsername(auth.getName());
        RentalCard existingRentalCard = rentalCardService.getRentalCardById(rentalCardId);
        if (!(user.getId().equals(existingRentalCard.getRenter().getId())
                ||user.getId().equals(existingRentalCard.getLessor().getId())
                || user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN")))){
            throw new ForbiddenException("It is not your rental card");
        }
        rentalCardService.deleteRentalCard(existingRentalCard);
        return ResponseEntity.noContent().build();
    }
}