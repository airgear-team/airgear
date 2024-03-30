package com.airgear.service.impl;

import com.airgear.exception.GoodsExceptions;
import com.airgear.exception.RegionExceptions;
import com.airgear.model.User;
import com.airgear.dto.AmountOfGoodsByCategoryResponse;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.TopGoodsPlacementDto;
import com.airgear.dto.TotalNumberOfGoodsResponse;
import com.airgear.dto.*;
import com.airgear.exception.ForbiddenException;
import com.airgear.exception.GoodsNotFoundException;
import com.airgear.mapper.CategoryMapper;
import com.airgear.mapper.GoodsMapper;
import com.airgear.mapper.LocationMapper;
import com.airgear.model.goods.Category;
import com.airgear.model.GoodsView;
import com.airgear.model.goods.Goods;
import com.airgear.model.goods.TopGoodsPlacement;
import com.airgear.model.location.Location;
import com.airgear.repository.*;
import com.airgear.service.GoodsService;
import com.airgear.service.GoodsStatusService;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.*;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static com.airgear.exception.UserExceptions.userNotFound;

@Service(value = "goodsService")
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsViewRepository goodsViewRepository;
    private final GoodsStatusService goodsStatusService;
    private final LocationRepository locationRepository;
    private final RegionsRepository regionsRepository;
    private final GoodsMapper goodsMapper;
    private final CategoryMapper categoryMapper;
    private final TopGoodsPlacementRepository topGoodsPlacementRepository;

    private final int MAX_GOODS_IN_CATEGORY_COUNT = 3;
    private static final int SIMILAR_GOODS_LIMIT = 12;
    private static final BigDecimal PRICE_VARIATION_PERCENTAGE = new BigDecimal("0.15");

    @Override
    public Goods getGoodsById(Long id) {
        Optional<Goods> goodsOptional = goodsRepository.findById(id);
        return goodsOptional.orElse(null);
    }

    @Override
    public GoodsDto getGoodsById(String ipAddress, String username, Long goodsId) {
        User user = userRepository.findByUsername(username);
        Goods goods = getGoodsById(goodsId);
        if (goods == null) {
            throw new GoodsNotFoundException("Goods not found");
        }
        if (!goods.getGoodsStatus().getName().equals("ACTIVE")) {
            throw new GoodsNotFoundException("Goods was deleted");
        }
        saveGoodsView(ipAddress, user.getId(), goods);
        return goodsMapper.toDto(goods);
    }
    @Override
    public void deleteGoods(Goods goods) {
        goods.setGoodsStatus(goodsStatusService.getGoodsById(2L));
        goods.setDeletedAt(OffsetDateTime.now());
        goodsRepository.save(goods);
    }

    @Override
    public void deleteGoods(String username, Long goodsId) {
        User user = userRepository.findByUsername(username);
        Goods goods = getGoodsById(goodsId);

        if (!user.getId().equals(goods.getUser().getId()) && !user.getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        deleteGoods(goods);
    }

    @Override
    public Goods saveGoods(@Valid Goods goods) {  // TODO to refactor this code
        checkCategory(goods);
        Long userId = goods.getUser().getId();
        int categoryId = goods.getCategory().getId();
        int productCount = goodsRepository.countByUserIdAndCategoryId(userId, categoryId);
        if (productCount >= MAX_GOODS_IN_CATEGORY_COUNT) {
            throw GoodsExceptions.goodsLimitExceeded(categoryId);
        }
        goods.setCreatedAt(OffsetDateTime.now());
        return goodsRepository.save(goods);
    }

    @Override
    public Goods updateGoods(@Valid Goods existingGoods) {
        //checkCategory(existingGoods);
        existingGoods.setLastModified(OffsetDateTime.now());
        return goodsRepository.save(existingGoods);
    }

    @Override
    public GoodsDto updateGoods(String username, Long goodsId, GoodsDto updatedGoodsDto) {
        User user = userRepository.findByUsername(username);
        Goods existingGoods = getGoodsById(goodsId);
        Goods updatedGoods = goodsMapper.toModel(updatedGoodsDto);
        if (!user.getId().equals(existingGoods.getUser().getId()) && !user.getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        if (updatedGoods.getName() != null) {
            existingGoods.setName(updatedGoods.getName());
        }
        if (updatedGoods.getDescription() != null) {
            existingGoods.setDescription(updatedGoods.getDescription());
        }
        if (updatedGoods.getPrice() != null) {
            existingGoods.setPrice(updatedGoods.getPrice());
        }
        if (updatedGoods.getLocation() != null) {
            existingGoods.setLocation(updatedGoods.getLocation());
        }
        return goodsMapper.toDto(updateGoods(existingGoods));
    }

    @Override
    public Set<GoodsDto> getAllGoodsByUsername(String username) {
        return goodsMapper.toDtoSet(goodsRepository.getGoodsByUserName(username));
    }

    @Override
    public Page<GoodsDto> listGoodsByName(Pageable pageable, String goodsName) {
        return null;
    }

    @Override
    public int getNewGoodsFromPeriod(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return goodsRepository.findCountNewGoodsFromPeriod(fromDate, toDate);
    }

    @Override
    public CountDeletedGoodsDTO countDeletedGoods(OffsetDateTime startDate, OffsetDateTime endDate, String category) {
        Long count = category != null ?
                goodsRepository.countByDeletedAtBetweenAndCategory(startDate, endDate, category) :
                goodsRepository.countByDeletedAtBetween(startDate, endDate);
        return new CountDeletedGoodsDTO(category, startDate, endDate, count);
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    @Override
    public List<GoodsDto> getRandomGoods(String categoryName, int quantity) {
        List<Goods> goods = getTopGoodsPlacements();
        List<Goods> randomGoods;
        Category category = convertStringToCategory(categoryName);
        if (category != null) {
            randomGoods = randomizeAndLimit(goodsRepository.findAllByCategory(category), quantity);
        } else {
            randomGoods = randomizeAndLimit(goodsRepository.findAll(), quantity);
        }
        goods.addAll(randomGoods);
        return goodsMapper.toDtoList(goods);
    }

    @Override
    public Page<GoodsDto> getSimilarGoods(String categoryName, BigDecimal price) {
        BigDecimal lowerBound = price.multiply(BigDecimal.ONE.subtract(PRICE_VARIATION_PERCENTAGE));
        BigDecimal upperBound = price.multiply(BigDecimal.ONE.add(PRICE_VARIATION_PERCENTAGE));

        Page<Goods> goods = filterGoods(categoryName, lowerBound, upperBound, PageRequest.of(0, SIMILAR_GOODS_LIMIT));
        return goods.map(goodsMapper::toDto);
    }

    @Override
    public AmountOfGoodsByCategoryResponse getAmountOfGoodsByCategory() {
        List<Goods> goodsList = goodsRepository.findAll();
        return new AmountOfGoodsByCategoryResponse(goodsList.stream()
                .collect(Collectors.groupingBy(goods -> categoryMapper.toDto(goods.getCategory()), Collectors.counting())));
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

    @Override
    public TotalNumberOfGoodsResponse getTotalNumberOfGoodsResponse() {
        return new TotalNumberOfGoodsResponse(goodsRepository.count());
    }

    @Override
    public TotalNumberOfTopGoodsResponse getTotalNumberOfTopGoodsResponse() {
        return new TotalNumberOfTopGoodsResponse(topGoodsPlacementRepository.countAllActivePlacements());

    }

    private void checkCategory(Goods goods) {
        if (goods.getCategory() != null) {
            Category category = categoryRepository.findByName(goods.getCategory().getName());
            if (category != null)
                goods.setCategory(category);
            else
                throw new RuntimeException("not correct category for good with id: " + goods.getId());
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

    @Override
    public Map<CategoryDto, Long> getAmountOfNewGoodsByCategory(OffsetDateTime fromDate, OffsetDateTime toDate) {
        List<Object> list = goodsRepository.findCountNewGoodsByCategoryFromPeriod(fromDate,toDate);
        return list==null?null:list.stream().map(x->(Object[])x).collect(Collectors.toMap(x->(CategoryDto) x[0], x->(Long)x[1]));
    }

    @Override
    public GoodsDto createGoods(String username, GoodsDto goodsDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw userNotFound(username);
        }
        Goods goods = goodsMapper.toModel(goodsDto);
        goods.setUser(user);
        goods.setGoodsStatus(goodsStatusService.getGoodsById(1L));
        goods.setCreatedAt(OffsetDateTime.now());

        LocationDto locationDto = goodsDto.getLocation();
        Location existingLocation = locationRepository.findBySettlementAndRegionId(locationDto.getSettlement(), locationDto.getRegionId());

        if (existingLocation != null) {
            goods.setLocation(existingLocation);
        } else {
            Location newLocation = Location.builder()
                    .settlement(locationDto.getSettlement())
                    .region(regionsRepository.findById(locationDto.getRegionId()).orElseThrow(() -> RegionExceptions.regionNotFound(locationDto.getRegionId())))
                    .build();

            goods.setLocation(locationRepository.save(newLocation));
        }
        return goodsMapper.toDto(goodsRepository.save(goods));
    }

    @Override
    public GoodsDto addToFavorites(String username, Long goodsId) {
        User user = userRepository.findByUsername(username);
        Goods goods = getGoodsById(goodsId);
        if (goods == null) {
            throw new GoodsNotFoundException("Goods not found");
        }

        if (user.getFavoriteGoods().contains(goods)) {
            user.getFavoriteGoods().remove(goods);
        } else {
            user.getFavoriteGoods().add(goods);
        }
        userRepository.save(user);
        return goodsMapper.toDto(goods);
    }

    @Override
    public List<Goods> getTopGoodsPlacements() {
        List<Goods> result = new ArrayList<>();
        topGoodsPlacementRepository.findAllActivePlacements().forEach(goods -> result.add(goods.getGoods()));
        return result;
    }

    @Override
    public TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto) {
        TopGoodsPlacement topGoodsPlacement = topGoodsPlacementDto.toModel();
        Goods goods = getGoodsById(topGoodsPlacement.getGoods().getId());
        Optional<User> userOptional = userRepository.findById(topGoodsPlacement.getUserId());
        if (userOptional.isEmpty()) {
            throw userNotFound(topGoodsPlacement.getUserId());
        }
        if (goods == null) {
            throw new GoodsNotFoundException("Goods not found");
        }
        if (!topGoodsPlacement.getUserId().equals(goods.getUser().getId()) && !userOptional.get().getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        return TopGoodsPlacementDto.toDto(topGoodsPlacementRepository.save(TopGoodsPlacement.builder()
                .userId(topGoodsPlacement.getUserId())
                .goods(goods)
                .startAt(topGoodsPlacement.getStartAt())
                .endAt(topGoodsPlacement.getEndAt()).build()));
    }
}