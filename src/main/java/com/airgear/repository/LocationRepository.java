package com.airgear.repository;

import com.airgear.model.goods.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findBySettlement(String settlement);

    Location findBySettlementAndRegionId(String settlement, Long regionId);
}
