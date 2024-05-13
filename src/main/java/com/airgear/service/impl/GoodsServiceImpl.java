package com.airgear.service.impl;

import com.airgear.dto.*;
import com.airgear.exception.GoodsExceptions;
import com.airgear.exception.LocationException;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.GoodsMapper;
import com.airgear.model.*;
import com.airgear.mapper.TopGoodsPlacementMapper;
import com.airgear.model.User;
import com.airgear.model.Category;
import com.airgear.model.Goods;
import com.airgear.model.TopGoodsPlacement;
import com.airgear.model.GoodsStatus;
import com.airgear.model.Location;
import com.airgear.repository.*;
import com.airgear.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static com.airgear.exception.UserExceptions.userNotFound;

@Service(value = "goodsService")
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final RegionsRepository regionsRepository;
    private final GoodsMapper goodsMapper;
    private final TopGoodsPlacementRepository topGoodsPlacementRepository;
    private final TopGoodsPlacementMapper topGoodsPlacementMapper;

    private static final int SIMILAR_GOODS_LIMIT = 12;
    private static final BigDecimal PRICE_VARIATION_PERCENTAGE = new BigDecimal("0.15");

    @Override
    public GoodsGetResponse getGoodsById(Long id) {
        Optional<Goods> goodsOptional = goodsRepository.findById(id);
        return goodsOptional.map(goodsMapper::toGetResponse).orElse(null);
    }

    @Override
    public GoodsGetResponse getGoodsById(String ipAddress, String email, Long goodsId) {
        GoodsGetResponse goods = getGoodsById(goodsId);
        if (goods == null || !goods.getStatus().equals(GoodsStatus.ACTIVE)) {
            throw GoodsExceptions.goodsNotFound(goodsId);
        }
        return goods;
    }

    @Override
    public void deleteGoods(Goods goods) {
        goods.setStatus(GoodsStatus.DELETED);
        goods.setDeletedAt(OffsetDateTime.now());
        goodsRepository.save(goods);
    }

    @Override
    public void deleteGoods(String email, Long goodsId) {
        User user = getUser(email);
        Goods goods = goodsMapper.toModel(getGoodsById(goodsId));

        if (!user.getId().equals(goods.getUser().getId()) && !user.getRoles().contains(Role.ADMIN)) {
            throw UserExceptions.AccessDenied("It is not your goods");
        }
        deleteGoods(goods);
    }

    @Override
    public Goods updateGoods(@Valid Goods existingGoods) {
        //checkCategory(existingGoods);
        existingGoods.setLastModified(OffsetDateTime.now());
        return goodsRepository.save(existingGoods);
    }

    @Override
    public GoodsUpdateResponse updateGoods(String email, Long goodsId, GoodsUpdateRequest goodsUpdateRequest) {
        User user = getUser(email);
        Goods existingGoods = goodsMapper.toModel(getGoodsById(goodsId));
        Goods updatedGoods = goodsMapper.toModel(goodsUpdateRequest);
        if (!user.getId().equals(existingGoods.getUser().getId()) && !user.getRoles().contains(Role.ADMIN)) {
            throw UserExceptions.AccessDenied("It is not your goods");
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
        return goodsMapper.toUpdateResponse(updateGoods(existingGoods));
    }

    @Override
    public Set<GoodsSearchResponse> getAllGoodsByUsername(String username) {
        return goodsMapper.toSearchResponse(goodsRepository.getGoodsByUserName(username));
    }

    @Override
    public List<GoodsGetRandomResponse> getRandomGoods(String categoryName, int quantity) {
        List<Goods> goods = getTopGoodsPlacements();
        List<Goods> randomGoods;
        Category category = convertStringToCategory(categoryName);
        if (category != null) {
            randomGoods = randomizeAndLimit(goodsRepository.findAllByCategory(category), quantity);
        } else {
            randomGoods = randomizeAndLimit(goodsRepository.findAll(), quantity);
        }
        goods.addAll(randomGoods);
        return goodsMapper.toGetRandomResponseList(goods);
    }

    @Override
    public Page<GoodsSearchResponse> getSimilarGoods(String categoryName, BigDecimal price) {
        BigDecimal lowerBound = price.multiply(BigDecimal.ONE.subtract(PRICE_VARIATION_PERCENTAGE));
        BigDecimal upperBound = price.multiply(BigDecimal.ONE.add(PRICE_VARIATION_PERCENTAGE));

        return filterGoods(categoryName, lowerBound, upperBound, PageRequest.of(0, SIMILAR_GOODS_LIMIT));
    }

    @Override
    public Page<GoodsGetResponse> getAllGoods(Pageable pageable) {
        return goodsRepository.findAll(pageable).map(goodsMapper::toGetResponse);
    }

    @Override
    public Page<GoodsSearchResponse> filterGoods(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Category category = convertStringToCategory(categoryName);

        if (category != null && minPrice != null && maxPrice != null) {
            return goodsRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageable)
                    .map(goodsMapper::toFilterResponse);
        } else if (minPrice != null && maxPrice != null) {
            return goodsRepository.findByPriceBetween(minPrice, maxPrice, pageable).map(goodsMapper::toFilterResponse);
        } else if (minPrice != null) {
            return goodsRepository.findByPriceGreaterThan(minPrice, pageable).map(goodsMapper::toFilterResponse);
        } else if (maxPrice != null) {
            return goodsRepository.findByPriceLessThan(maxPrice, pageable).map(goodsMapper::toFilterResponse);
        } else if (category != null) {
            return goodsRepository.findByCategory(category, pageable).map(goodsMapper::toFilterResponse);
        } else {
            return goodsRepository.findAll(pageable).map(goodsMapper::toFilterResponse);
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
    public GoodsCreateResponse createGoods(String email, GoodsCreateRequest request) {
        User user = getUser(email);
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            request.setPhoneNumber(user.getPhone());
        }

        Goods goods = goodsMapper.toModel(request);
        goods.setUser(user);
        goods.setStatus(GoodsStatus.ACTIVE);
        goods.setCreatedAt(OffsetDateTime.now());

        Location existingLocation = locationRepository.findByUniqueSettlementID(Math.toIntExact(request.getLocationId()));

        if (existingLocation != null) {
            goods.setLocation(existingLocation);
        } else {
            LocationException.locationNotFound(Math.toIntExact(request.getLocationId()));
        }
        return goodsMapper.toCreateResponse(goodsRepository.save(goods));
    }

    @Override
    public GoodsGetResponse addToFavorites(String email, Long goodsId) {
        User user = getUser(email);
        Goods goods = goodsMapper.toModel(getGoodsById(goodsId));
        if (goods == null) {
            throw GoodsExceptions.goodsNotFound(goodsId);
        }

        if (user.getFavoriteGoods().contains(goods)) {
            user.getFavoriteGoods().remove(goods);
        } else {
            user.getFavoriteGoods().add(goods);
        }
        userRepository.save(user);
        return goodsMapper.toGetResponse(goods);
    }

    @Override
    public List<Goods> getTopGoodsPlacements() {
        List<Goods> result = new ArrayList<>();
        topGoodsPlacementRepository.findAllActivePlacements().forEach(goods -> result.add(goods.getGoods()));
        return result;
    }

    @Override
    public TopGoodsPlacementDto addTopGoodsPlacements(TopGoodsPlacementDto topGoodsPlacementDto) {
        TopGoodsPlacement topGoodsPlacement = topGoodsPlacementMapper.toModel(topGoodsPlacementDto);
        Goods goods = goodsRepository.findById(topGoodsPlacement.getGoods().getId()).orElse(null);
        Optional<User> userOptional = userRepository.findById(topGoodsPlacement.getUserId());
        if (userOptional.isEmpty()) {
            throw userNotFound(topGoodsPlacement.getUserId());
        }
        if (goods == null) {
            throw GoodsExceptions.goodsNotFound(topGoodsPlacement.getGoods().getId());
        }
        if (!topGoodsPlacement.getUserId().equals(goods.getUser().getId()) && !userOptional.get().getRoles().contains(Role.ADMIN)) {
            throw UserExceptions.AccessDenied("It is not your goods");
        }
        return topGoodsPlacementMapper.toDto(topGoodsPlacementRepository.save(TopGoodsPlacement.builder()
                .userId(topGoodsPlacement.getUserId())
                .goods(goods)
                .startAt(topGoodsPlacement.getStartAt())
                .endAt(topGoodsPlacement.getEndAt()).build()));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserExceptions.userNotFound(email));
    }
}
