package com.airgear.repository;

import com.airgear.model.goods.GoodsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsStatusRepository extends JpaRepository<GoodsStatus, Long> {
}