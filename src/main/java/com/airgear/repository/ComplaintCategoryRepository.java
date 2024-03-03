package com.airgear.repository;

import com.airgear.model.ComplaintCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {
    ComplaintCategory findByName(String name);
}
