package com.airgear.repository;

import com.airgear.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionsRepository extends JpaRepository<Region, Long> {
}
