package com.airgear.repository;

import com.airgear.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findBySettlement(String settlement);

    Location findByUniqueSettlementID(Integer uniqueSettlementID);
}
