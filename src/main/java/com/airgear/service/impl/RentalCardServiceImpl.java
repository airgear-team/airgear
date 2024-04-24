package com.airgear.service.impl;

import com.airgear.dto.*;
import com.airgear.exception.GoodsExceptions;
import com.airgear.exception.RentalCardExceptions;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.RentalCardMapper;
import com.airgear.model.Goods;
import com.airgear.model.RentalCard;
import com.airgear.model.User;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.RentalCardRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.RentalCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class RentalCardServiceImpl implements RentalCardService {

    private final RentalCardRepository rentalCardRepository;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final RentalCardMapper rentalCardMapper;
    private final GoodsMapper goodsMapper;

    @Override
    public RentalCardResponse create(RentalCardSaveRequest request) {
        return rentalCardMapper.toDto(save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarDayResponse> getCalendarForGoods(long goodsId, OffsetDateTime fromDate, OffsetDateTime toDate) {
        List<RentalCard> list = rentalCardRepository.findRentalCardsByPeriod(goodsId, fromDate, toDate);
        Map<LocalDate, Set<DayTimeResponse>> map = new HashMap<>();

        LocalDate date = fromDate.toLocalDate();
        while (!date.isAfter(toDate.toLocalDate())) {
            map.put(date, new TreeSet<>(Comparator.comparing(DayTimeResponse::getTimeFrom)));
            date = date.plusDays(1);
        }

        for (RentalCard card : list) {
            LocalDate firstDate = card.getFirstDate().toLocalDate();
            LocalDate lastDate = card.getLastDate().toLocalDate();

            for (LocalDate rentalDate = firstDate; !rentalDate.isAfter(lastDate); rentalDate = rentalDate.plusDays(1)) {
                Set<DayTimeResponse> dayTimeResponseSet = map.get(rentalDate);
                if (dayTimeResponseSet != null) {
                    LocalTime startTime = rentalDate.equals(fromDate.toLocalDate()) ? fromDate.toLocalTime() : LocalTime.MIN;
                    LocalTime endTime = rentalDate.equals(toDate.toLocalDate()) ? toDate.toLocalTime() : LocalTime.MAX;
                    dayTimeResponseSet.add(new DayTimeResponse(startTime, endTime, false));
                }
            }
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new CalendarDayResponse(entry.getKey(), entry.getValue().isEmpty(), goodsId,
                        entry.getValue().isEmpty() ? null : entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RentalCardResponse getById(long id) {
        return rentalCardMapper.toDto(getRentalCard(id));
    }

    @Override
    public RentalCardResponse changeDurationById(long id, RentalCardChangeDurationRequest request) {
        RentalCard rentalCard = getRentalCard(id);
        return rentalCardMapper.toDto(changeDuration(rentalCard, request));
    }

    @Override
    public void deleteById(long id) {
        RentalCard rentalCard = getRentalCard(id);
        rentalCard.setDeletedAt(OffsetDateTime.now());
    }

    private RentalCard save(RentalCardSaveRequest request) {
        RentalCard rentalCard = new RentalCard();
        rentalCard.setCreatedAt(OffsetDateTime.now());
        rentalCard.setLessor(getUser(request.getLessorId()));
        rentalCard.setRenter(getUser(request.getRenterId()));
        rentalCard.setFirstDate(request.getFirstDate());
        rentalCard.setLastDate(request.getLastDate());
        rentalCard.setDuration(request.getDuration());
        rentalCard.setQuantity(request.getQuantity());
        rentalCard.setGoods(getGoods(request.getGoodsId()));
        rentalCard.setRentalPrice(request.getPrice());
        rentalCard.setFine(request.getFine());
        rentalCard.setDescription(request.getDescription());
        return rentalCardRepository.save(rentalCard);
    }

    private User getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserExceptions.userNotFound(id));
    }

    private Goods getGoods(long id) {
        return goodsRepository.findById(id)
                .orElseThrow(() -> GoodsExceptions.goodsNotFound(id));
    }

    public RentalCard changeDuration(RentalCard rentalCard, RentalCardChangeDurationRequest request) {
        rentalCard.setFirstDate(request.getFirstDate());
        rentalCard.setLastDate(request.getLastDate());
        rentalCard.setDuration(request.getDuration());
        return rentalCard;
    }

    private RentalCard getRentalCard(long id) {
        return rentalCardRepository.findById(id)
                .orElseThrow(() -> RentalCardExceptions.rentalCardNotFound(id));
    }
}
