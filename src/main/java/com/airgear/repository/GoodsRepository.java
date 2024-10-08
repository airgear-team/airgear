package com.airgear.repository;

import com.airgear.model.Category;
import com.airgear.model.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigDecimal;
import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Set<Goods> getGoodsByUserName(String username);

    Page<Goods> findAllByNameLikeIgnoreCase(Pageable pageable, String goodsName);

    List<Goods> findAll();

    @Query("FROM Category category WHERE category.name = :name")
    Category getCategoryByName(@Param("name") String name);

    Page<Goods> findAll(Pageable pageable);

    List<Goods> findAllByCategory(Category category);

    Page<Goods> findByCategory(Category category, Pageable pageable);

    Page<Goods> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Goods> findByPriceGreaterThan(BigDecimal minPrice, Pageable pageable);

    Page<Goods> findByPriceLessThan(BigDecimal maxPrice, Pageable pageable);

    Page<Goods> findByCategoryAndPriceBetween(Category category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    int countByUserIdAndCategoryId(Long userId, Integer categoryId);
}