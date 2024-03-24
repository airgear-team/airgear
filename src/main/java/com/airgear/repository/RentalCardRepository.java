package com.airgear.repository;

import com.airgear.model.RentalCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface RentalCardRepository extends JpaRepository<RentalCard, Long> {
    @Query("FROM RentalCard rentalCard WHERE rentalCard.goods.id = :goodsId  and  rentalCard.lastDate >= :fromDate AND rentalCard.firstDate <= :toDate")
    List<RentalCard> findRentalCardsByPeriod(Long goodsId, OffsetDateTime fromDate, OffsetDateTime toDate);

}