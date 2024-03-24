package com.airgear.service.impl;

import com.airgear.dto.CalendarDay;
import com.airgear.dto.RentalCardDto;
import com.airgear.dto.DayTime;
import com.airgear.model.RentalCard;
import com.airgear.repository.RentalCardRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalCardService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalCardServiceImpl implements RentalCardService {

    private final RentalCardRepository rentalCardRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GoodsService goodsService;

    @Override
    public RentalCard getRentalCardById(Long id) {
        return rentalCardRepository.getReferenceById(id);
    }

    @Override
    public void deleteRentalCard(RentalCard rentalCard) {
        rentalCard.setDeletedAt(OffsetDateTime.now());
        rentalCardRepository.save(rentalCard);
    }

    @Override
    public RentalCard saveRentalCard(RentalCardDto rentalCardDto) {
        RentalCard rentalCard = rentalCardDto.getRentalCardFromDto();
        rentalCard.setRenter(userRepository.findByUsername(rentalCardDto.getRenterUsername()));
        rentalCard.setLessor(userRepository.findByUsername(rentalCardDto.getLessorUsername()));
        rentalCard.setGoods(goodsService.getGoodsById(rentalCardDto.getGoodsId()));
        rentalCard.setCreatedAt(OffsetDateTime.now());
        checkDays(rentalCard);
        return rentalCardRepository.save(rentalCard);
    }

    @Override
    public RentalCard updateRentalCard(RentalCardDto rentalCardDto) {
        return saveRentalCard(rentalCardDto);
    }
    private void checkDays(RentalCard rentalCard){
        if(rentalCard.getDuration()==null&&rentalCard.getLastDate()==null){
            throw new RuntimeException("Don't fill duration or last day in rental card!");
        }
        else if(rentalCard.getDuration()==null){
            rentalCard.setDuration(ChronoUnit.DAYS);
        }
        else{
            rentalCard.setLastDate(rentalCard.getFirstDate().plus(rentalCard.getQuantity(), rentalCard.getDuration()));
        }
    }

    @Override
    public List<CalendarDay> getCalendarForGoods(Long goodsId, OffsetDateTime fromDate, OffsetDateTime toDate){
        List<RentalCard> list =rentalCardRepository.findRentalCardsByPeriod(goodsId,fromDate, toDate);
        Map<LocalDate, Set<DayTime>> map = fromDate.toLocalDate()
                .datesUntil(toDate.toLocalDate())
                .collect(Collectors.toMap(x->x, x->new TreeSet<>(Comparator.comparing(DayTime::getTimeFrom))));

        for (Map.Entry<LocalDate, Set<DayTime>> entry : map.entrySet()) {
            for (RentalCard card:list) {
                if((card.getFirstDate().toLocalDate().isBefore(entry.getKey())||card.getFirstDate().toLocalDate().isEqual(entry.getKey()))
                        && (card.getLastDate().toLocalDate().isAfter(entry.getKey())||card.getLastDate().toLocalDate().isEqual(entry.getKey()))){
                    if(card.getDuration()== ChronoUnit.HOURS){
                        entry.getValue().add(new DayTime(card.getFirstDate().toLocalTime(), card.getLastDate().toLocalTime(), false));
                    }
                    else{
                        entry.getValue().add(new DayTime(LocalTime.MIN, LocalTime.MAX, false));
                    }
                }
            }
        }
        List<CalendarDay> listCalendar =new ArrayList<>();
        for (Map.Entry<LocalDate, Set<DayTime>> entry : map.entrySet()) {
            boolean isEmpty = entry.getValue().isEmpty();
            listCalendar.add(new CalendarDay(entry.getKey(),isEmpty, goodsId, isEmpty?null:getDayTime(fromDate, toDate, entry)));
        }
        return listCalendar.stream().sorted(Comparator.comparing(CalendarDay::getDate)).collect(Collectors.toList());
    }

    private Set<DayTime> getDayTime(OffsetDateTime fromDate, OffsetDateTime toDate, Map.Entry<LocalDate, Set<DayTime>> entry) {
        Set<DayTime> setTime =new TreeSet<>(Comparator.comparing(DayTime::getTimeFrom));
        LocalTime localTimeStart = fromDate.toLocalDate().isEqual(entry.getKey()) ? fromDate.toLocalTime() : LocalTime.MIN;
        LocalTime localTimeEnd = toDate.toLocalDate().isEqual(entry.getKey()) ? toDate.toLocalTime() : LocalTime.MAX;
        for (DayTime el : entry.getValue()) {
            if(el.getTimeFrom().equals(LocalTime.MIN)&&el.getTimeTo().equals(LocalTime.MAX)){
                return null;
            }
            else if (el.getTimeFrom().isBefore(localTimeStart)||el.getTimeFrom().equals(localTimeStart)){
                setTime.add(new DayTime(localTimeStart, el.getTimeTo().isBefore(localTimeEnd)?el.getTimeTo():localTimeEnd, false));
                localTimeStart= el.getTimeTo().isBefore(localTimeEnd)?el.getTimeTo():localTimeEnd;
            }
            else if(el.getTimeFrom().isBefore(localTimeEnd)) {
                setTime.add(new DayTime(localTimeStart, el.getTimeFrom().isBefore(localTimeEnd)?el.getTimeFrom():localTimeEnd, true));
                setTime.add(new DayTime(el.getTimeFrom(), el.getTimeTo().isBefore(localTimeEnd)?el.getTimeTo():localTimeEnd, false));
                localTimeStart= el.getTimeTo().isBefore(localTimeEnd)?el.getTimeTo():localTimeEnd;
            }
        }
        if (localTimeStart.isBefore(localTimeEnd)) {
            setTime.add(new DayTime(localTimeStart, localTimeEnd, true));
        }
        return setTime;
    }
}