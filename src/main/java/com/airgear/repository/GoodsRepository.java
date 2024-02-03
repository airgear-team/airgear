package com.airgear.repository;

import com.airgear.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Set<Goods> getGoodsByUserName(String username);
}