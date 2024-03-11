package com.airgear.service.impl;

import com.airgear.dto.RentalCardDto;
import com.airgear.model.RentalCard;
import com.airgear.repository.RentalCardRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalCardService;
import com.airgear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class RentalCardServiceImpl implements RentalCardService {

    @Autowired
    private RentalCardRepository rentalCardRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

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
        rentalCard.setRenter(userService.findByUsername(rentalCardDto.getRenterUsername()));
        rentalCard.setLessor(userService.findByUsername(rentalCardDto.getLessorUsername()));
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
}