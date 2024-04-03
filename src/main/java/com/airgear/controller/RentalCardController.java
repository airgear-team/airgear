package com.airgear.controller;

import com.airgear.dto.CalendarDay;
import com.airgear.dto.RentalCardDto;
import com.airgear.dto.UserDto;
import com.airgear.exception.UserExceptions;
import com.airgear.model.RentalCard;
import com.airgear.service.RentalCardService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/rental")
@AllArgsConstructor
public class RentalCardController {

    private final RentalCardService rentalCardService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @RequestMapping(value = "/{rentalCardId}", method = RequestMethod.GET)
    public RentalCard getRentalCardById(@PathVariable Long rentalCardId) {
        return rentalCardService.getRentalCardById(rentalCardId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public RentalCard createRentalCard(@RequestBody RentalCardDto rentalCardDto) {
        return rentalCardService.saveRentalCard(rentalCardDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PutMapping("/{rentalCardId}")
    public ResponseEntity<RentalCardDto> updateRentalCard(
            Authentication auth,
            @PathVariable Long rentalCardId,
            @Valid @RequestBody RentalCardDto updatedRentalCard) {
        UserDto user = userService.findByUsername(auth.getName());
        RentalCard existingRentalCard = rentalCardService.getRentalCardById(rentalCardId);
        if (!(user.getId().equals(existingRentalCard.getRenter().getId())
                ||user.getId().equals(existingRentalCard.getLessor().getId())
                || user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN")))){
            throw UserExceptions.AccessDenied("It is not your rental card");
        }
        return ResponseEntity.ok(updatedRentalCard);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{rentalCardId}")
    public ResponseEntity<String> deleteRentalCard(Authentication auth, @PathVariable Long rentalCardId){
        UserDto user = userService.findByUsername(auth.getName());
        RentalCard existingRentalCard = rentalCardService.getRentalCardById(rentalCardId);
        if (!(user.getId().equals(existingRentalCard.getRenter().getId())
                ||user.getId().equals(existingRentalCard.getLessor().getId())
                || user.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN")))){
            throw UserExceptions.AccessDenied("It is not your rental card");
        }
        rentalCardService.deleteRentalCard(existingRentalCard);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/calendar/{goodsId}")
    public ResponseEntity<List<CalendarDay>> getCalendarForGoods(@PathVariable Long goodsId,
                                                                 @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromDate,
                                                                 @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toDate
    ){
        List<CalendarDay> list = rentalCardService.getCalendarForGoods(goodsId,fromDate,toDate);
        return ResponseEntity.ok(list);
    }
}