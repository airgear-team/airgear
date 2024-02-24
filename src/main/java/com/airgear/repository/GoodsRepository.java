package com.airgear.repository;

import com.airgear.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Set<Goods> getGoodsByUserName(String username);
    @Query(value = "SELECT * FROM goods ORDER BY RAND() LIMIT :goodsQuantity", nativeQuery = true)
    List<Goods> getRandomGoods(@Param("goodsQuantity") int goodsQuantity);
}