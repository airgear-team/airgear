package com.airgear.service.impl;

import com.airgear.model.goods.Category;
import com.airgear.model.GoodsView;
import com.airgear.model.goods.Goods;
import com.airgear.model.goods.response.GoodsResponse;
import com.airgear.repository.CategoryRepository;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.GoodsViewRepository;
import com.airgear.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GoodsViewRepository goodsViewRepository;

    @Override
    public Goods getGoodsById(Long id) {
        Optional<Goods> goodsOptional = goodsRepository.findById(id);
        return goodsOptional.orElse(null);
    }

    @Override
    public void deleteGoods(Goods goods) {
        goods.setDeletedAt(OffsetDateTime.now());
        goodsRepository.save(goods);
    }

    @Override
    public Goods saveGoods(@Valid Goods goods) {
        //checkCategory(goods);
        goods.setCreatedAt(OffsetDateTime.now());
        return goodsRepository.save(goods);
    }

    @Override
    public Goods updateGoods(Goods existingGoods) {
        //checkCategory(existingGoods);
        existingGoods.setLastModified(OffsetDateTime.now());
        return goodsRepository.save(existingGoods);
    }

    @Override
    public Set<Goods> getAllGoodsByUsername(String username) {
        Set<Goods> goodsSet = goodsRepository.getGoodsByUserName(username);
        return goodsSet;
    }

    @Override
    public Page<GoodsResponse> listGoodsByName(Pageable pageable, String goodsName) {
        return null;
    }

    @Override
    public int getNewGoodsFromPeriod(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return goodsRepository.findCountNewGoodsFromPeriod(fromDate, toDate);
    }

    public Long countDeletedGoods(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return goodsRepository.countByDeletedAtBetween(fromDate, toDate);
    }

    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = goodsRepository.findAll();
        return goodsList;
    }

    @Override
    public List<Goods> getRandomGoods(String categoryName, int quantity) {
        List<Goods> goods;
        Category category = convertStringToCategory(categoryName);

        if (category != null) {
            goods = goodsRepository.findAllByCategory(category);
        } else {
            goods = goodsRepository.findAll();
        }

        return randomizeAndLimit(goods, quantity);
    }


    @Override
    public Map<Category, Long> getAmountOfGoodsByCategory() {
        List<Goods> goodsList = goodsRepository.findAll();

        // Grouping by category and quantity
        return goodsList.stream()
                .collect(Collectors.groupingBy(Goods::getCategory, Collectors.counting()));
    }

    @Override
    public Page<Goods> getAllGoods(Pageable pageable) {
        return goodsRepository.findAll(pageable);
    }

    @Override
    public Page<Goods> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Category category = convertStringToCategory(categoryName);

        if (category != null && minPrice != null && maxPrice != null) {
            return goodsRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageable);
        } else if (minPrice != null && maxPrice != null) {
            return goodsRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (minPrice != null) {
            return goodsRepository.findByPriceGreaterThan(minPrice, pageable);
        } else if (maxPrice != null) {
            return goodsRepository.findByPriceLessThan(maxPrice, pageable);
        } else if (category != null) {
            return goodsRepository.findByCategory(category, pageable);
        } else {
            return goodsRepository.findAll(pageable);
        }
    }

    @Override
    public Long getTotalNumberOfGoods() {
        return goodsRepository.count();
    }

    private void checkCategory(Goods goods){
        if (goods.getCategory()!=null){
            Category category= categoryRepository.findByName(goods.getCategory().getName());
            if (category!=null)
                goods.setCategory(category);
            else
                throw new RuntimeException("not correct category for good with id: "+goods.getId());
        }
    }


    private Category convertStringToCategory(String categoryName) {
        if (categoryName != null) {
            return categoryRepository.findCategoryByName(categoryName);
        }
        return null;
    }

    private static List<Goods> randomizeAndLimit(List<Goods> goods, int limit) {
        Collections.shuffle(goods);
        return goods.subList(0, Math.min(goods.size(), limit));
    }
    @Override
    public void saveGoodsView(String ip, Long userId, Goods goods) {
        if (goodsViewRepository.existsByIpAndGoods(ip, goods)) {
            if (userId != null && !goodsViewRepository.existsByUserIdAndGoods(userId, goods)) {
                goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
            }
            return;
        }
        goodsViewRepository.save(new GoodsView(userId, ip, OffsetDateTime.now(), goods));
    }

}