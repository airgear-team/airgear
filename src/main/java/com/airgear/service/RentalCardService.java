package com.airgear.service;

import com.airgear.dto.CalendarDayResponse;
import com.airgear.dto.RentalCardChangeDurationRequest;
import com.airgear.dto.RentalCardResponse;
import com.airgear.dto.RentalCardSaveRequest;

import java.time.OffsetDateTime;
import java.util.List;

public interface RentalCardService {

    RentalCardResponse create(RentalCardSaveRequest request);

    List<CalendarDayResponse> getCalendarForGoods(long goodsId, OffsetDateTime fromDate, OffsetDateTime toDate);

    RentalCardResponse getById(long id);

    RentalCardResponse changeDurationById(long id, RentalCardChangeDurationRequest request);

    void deleteById(long id);

}
