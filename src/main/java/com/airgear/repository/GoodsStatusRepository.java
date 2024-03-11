package com.airgear.repository;

import com.airgear.model.goods.GoodsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsStatusRepository extends JpaRepository<GoodsStatus, Long> {
}