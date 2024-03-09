package com.airgear.repository;

import com.airgear.model.ComplaintCategory;
import com.airgear.model.goods.Category;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);

    Category findByName(String name);

}
