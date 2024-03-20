package com.airgear.service;

import com.airgear.dto.CalendarDay;
import com.airgear.dto.RentalCardDto;
import com.airgear.model.RentalCard;

import java.time.OffsetDateTime;
import java.util.List;

public interface RentalCardService {

    RentalCard getRentalCardById(Long id);
    void deleteRentalCard(RentalCard rentalCard);
    RentalCard saveRentalCard(RentalCardDto rentalCard);
    RentalCard updateRentalCard(RentalCardDto rentalCard);

    List<CalendarDay> getCalendarForGoods(Long goodsId, OffsetDateTime fromDate, OffsetDateTime toDate);
}