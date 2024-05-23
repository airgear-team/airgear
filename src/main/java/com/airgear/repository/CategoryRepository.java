package com.airgear.repository;

import com.airgear.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findCategoryByName(String name);

    Category findByName(String name);

}
