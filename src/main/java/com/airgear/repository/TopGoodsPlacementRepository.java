package com.airgear.repository;

import com.airgear.model.goods.TopGoodsPlacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopGoodsPlacementRepository extends JpaRepository<TopGoodsPlacement, Long> {

    @Query("SELECT t FROM TopGoodsPlacement t WHERE t.startAt <= CURRENT_TIMESTAMP AND t.endAt >= CURRENT_TIMESTAMP")
    List<TopGoodsPlacement> findAllActivePlacements();

    @Query("SELECT COUNT(t) FROM TopGoodsPlacement t WHERE t.startAt <= CURRENT_TIMESTAMP AND t.endAt >= CURRENT_TIMESTAMP")
    Long countAllActivePlacements();

}
