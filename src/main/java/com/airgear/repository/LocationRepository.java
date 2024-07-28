package com.airgear.repository;

import com.airgear.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByUniqueSettlementID(Integer uniqueSettlementID);

    @Query("select l from Location l where l.settlement like concat(:name, '%')")
    Page<Location> findAllBySettlementContainingIgnoreCase(Pageable pageable, String name);

}
