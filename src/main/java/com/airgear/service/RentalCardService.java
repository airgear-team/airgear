package com.airgear.service;

import com.airgear.dto.RentalCardDto;
import com.airgear.model.RentalCard;

public interface RentalCardService {

    RentalCard getRentalCardById(Long id);
    void deleteRentalCard(RentalCard rentalCard);
    RentalCard saveRentalCard(RentalCardDto rentalCard);
    RentalCard updateRentalCard(RentalCardDto rentalCard);
}