package com.airgear.repository;

import com.airgear.model.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionsRepository extends JpaRepository<Region, Long> {
}
