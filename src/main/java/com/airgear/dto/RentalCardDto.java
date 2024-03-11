package com.airgear.dto;


import com.airgear.model.RentalCard;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class RentalCardDto {

        private long id;
        private String lessorUsername;
        private String renterUsername;
        private OffsetDateTime firstDate;
        private OffsetDateTime lastDate;
        private ChronoUnit duration;
        private Long quantity;
        private Long goodsId;
        private BigDecimal rentalPrice;
        private BigDecimal fine;
        private String description;



        public RentalCard getRentalCardFromDto(){
            User lessor = new User();
            lessor.setUsername(lessorUsername);
            User renter = new User();
            renter.setUsername(renterUsername);
            Goods goods = new Goods();
            goods.setId(goodsId);
            RentalCard rentalCard = new RentalCard();
            rentalCard.setId(id);
            rentalCard.setLessor(lessor);
            rentalCard.setRenter(renter);
            rentalCard.setFirstDate(firstDate);
            rentalCard.setLastDate(lastDate);
            rentalCard.setDescription(description);
            rentalCard.setGoods(goods);
            rentalCard.setRentalPrice(rentalPrice);
            rentalCard.setFine(fine);
            rentalCard.setQuantity(quantity);
            rentalCard.setDuration(duration);
            return rentalCard;
        }

        public static RentalCardDto getDtoFromRentalCard(RentalCard rentalCard){
            return RentalCardDto.builder()
                    .id(rentalCard.getId())
                    .lessorUsername(rentalCard.getLessor().getUsername())
                    .renterUsername(rentalCard.getRenter().getUsername())
                    .firstDate(rentalCard.getFirstDate())
                    .duration(rentalCard.getDuration())
                    .quantity(rentalCard.getQuantity())
                    .lastDate(rentalCard.getLastDate())
                    .description(rentalCard.getDescription())
                    .goodsId(rentalCard.getGoods().getId())
                    .rentalPrice(rentalCard.getRentalPrice())
                    .fine(rentalCard.getFine())
                    .quantity(rentalCard.getQuantity()).build();
        }
    }

