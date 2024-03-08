package com.airgear.repository;

import com.airgear.model.ComplaintCategory;
import com.airgear.model.goods.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}