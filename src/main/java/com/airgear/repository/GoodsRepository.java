package com.airgear.repository;

import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Set<Goods> getGoodsByUserName(String username);

    Page<Goods> findAll(Pageable pageable);

    Page<Goods> findByCategory(Category category, Pageable pageable);

    Page<Goods> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Goods> findByPriceGreaterThan(BigDecimal minPrice, Pageable pageable);

    Page<Goods> findByPriceLessThan(BigDecimal maxPrice, Pageable pageable);

    Page<Goods> findByCategoryAndPriceBetween(Category category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);


}