package com.airgear.repository;

import com.airgear.model.RentalCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalCardRepository extends JpaRepository<RentalCard, Long> {
}