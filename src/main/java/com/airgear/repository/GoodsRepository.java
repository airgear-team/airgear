package com.airgear.repository;

import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.airgear.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Set<Goods> getGoodsByUserName(String username);

    Page<Goods> findAllByNameLikeIgnoreCase(Pageable pageable, String goodsName);

    @Query("SELECT count(id) FROM Goods goods WHERE goods.createdAt >= :fromDate AND goods.createdAt <= :toDate")
    int  findCountNewGoodsFromPeriod(@Param("fromDate")OffsetDateTime fromDate, @Param("toDate") OffsetDateTime toDate);
}