package com.airgear.controller;

import com.airgear.dto.CalendarDayResponse;
import com.airgear.dto.RentalCardChangeDurationRequest;
import com.airgear.dto.RentalCardResponse;
import com.airgear.dto.RentalCardSaveRequest;
import com.airgear.service.RentalCardService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/rental")
@AllArgsConstructor
public class RentalCardController {

    private final RentalCardService rentalCardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RentalCardResponse> create(@RequestBody @Valid RentalCardSaveRequest request,
                                                     UriComponentsBuilder ucb) {
        RentalCardResponse response = rentalCardService.create(request);
        return ResponseEntity
                .created(ucb.path("/rental/{id}").build(response.getId()))
                .body(response);
    }

    @GetMapping("/{goodsId}/calendar")
    public List<CalendarDayResponse> getCalendarForGoods(
            @PathVariable long goodsId,
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toDate) {
        return rentalCardService.getCalendarForGoods(goodsId, fromDate, toDate);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RentalCardResponse getById(@PathVariable long id) {
        return rentalCardService.getById(id);
    }

    @PatchMapping("/{id}")
    public RentalCardResponse changeDurationById(@PathVariable Long id,
                                                 @RequestBody @Valid RentalCardChangeDurationRequest request) {
        return rentalCardService.changeDurationById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        rentalCardService.deleteById(id);
    }
}
