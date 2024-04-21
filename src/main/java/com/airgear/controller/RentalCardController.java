package com.airgear.controller;

import com.airgear.dto.CalendarDayResponse;
import com.airgear.dto.RentalCardChangeDurationRequest;
import com.airgear.dto.RentalCardResponse;
import com.airgear.dto.RentalCardSaveRequest;
import com.airgear.service.RentalCardService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/rental")
@AllArgsConstructor
public class RentalCardController {

    private final RentalCardService rentalCardService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public RentalCardResponse create(@RequestBody @Valid RentalCardSaveRequest request) {
        return rentalCardService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{goodsId}/calendar")
    public List<CalendarDayResponse> getCalendarForGoods(
            @PathVariable long goodsId,
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toDate) {
        return rentalCardService.getCalendarForGoods(goodsId, fromDate, toDate);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RentalCardResponse getById(@PathVariable long id) {
        return rentalCardService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PatchMapping("/{id}")
    public RentalCardResponse changeDurationById(@PathVariable Long id,
                                                 @RequestBody @Valid RentalCardChangeDurationRequest request) {
        return rentalCardService.changeDurationById(id, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        rentalCardService.deleteById(id);
    }
}
