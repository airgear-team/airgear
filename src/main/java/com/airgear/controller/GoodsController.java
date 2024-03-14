package com.airgear.controller;

import com.airgear.dto.ComplaintDTO;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.UserDto;
import com.airgear.exception.ForbiddenException;
import com.airgear.model.goods.Category;
import com.airgear.model.Complaint;
import com.airgear.model.GoodsView;
import com.airgear.model.goods.Goods;
import com.airgear.model.goods.GoodsStatus;
import com.airgear.model.RentalAgreement;
import com.airgear.model.User;
import com.airgear.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.airgear.service.ComplaintService;
import com.airgear.repository.GoodsStatusRepository;
import com.airgear.repository.GoodsViewRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import com.airgear.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {

    //TODO
    // 1. Інжектити змінні за допомогою контролера
    // 2. Внести всю логіку в сервіси
    // 3. Зробити власні ексепшений
    // 4. Закрити всі тудушки в класі
    // 5. Протестувати всі ендпоїнити на працездатність


    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsStatusService goodsStatusService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private RentalAgreementService rentalAgreementService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public Goods createGoods(Authentication auth, @RequestBody GoodsDto goods) {
        User user = userService.findByUsername(auth.getName());
        Goods newGoods = goods.toGoods();
        newGoods.setUser(user);
//        newGoods.setLocation(locationService.addLocation(goods.getLocation().toLocation()));
        GoodsStatus status = goodsStatusService.getGoodsById(1L);
        newGoods.setGoodsStatus(status);
        return goodsService.saveGoods(newGoods);
    }

    // TODO створити власні ексепшени для всіх проблем які можуть бути в цьому конроллері
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{goodsId}")
    public ResponseEntity<Goods> getGoodsById(HttpServletRequest request, Authentication auth, @PathVariable Long goodsId) {
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods == null) {
            throw new ForbiddenException("Goods not found");
        }
        if (!goods.getGoodsStatus().getName().equals("ACTIVE")) {
            throw new ForbiddenException("Goods was deleted");
        }
        goodsService.saveGoodsView(request.getRemoteAddr(), user.getId(), goods);
        return ResponseEntity.ok(goods);
    }

    // TODO створити власні ексепшени для всіх проблем які можуть бути в цьому конроллері
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PutMapping("/{goodsId}")
    public ResponseEntity<Goods> updateGoods(
            Authentication auth,
            @PathVariable Long goodsId,
            @Valid @RequestBody Goods updatedGoods) {
        User user = userService.findByUsername(auth.getName());
        Goods existingGoods = goodsService.getGoodsById(goodsId);
        if (user.getId() != existingGoods.getUser().getId() && !user.getRoles().contains("ADMIN")) {
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
        Goods updatedGoodsEntity = goodsService.updateGoods(existingGoods);
        return ResponseEntity.ok(updatedGoodsEntity);
    }

    // TODO створити власні ексепшени для всіх проблем які можуть бути в цьому конроллері
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(Authentication auth, @PathVariable Long goodsId) {
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        if (user.getId() != goods.getUser().getId() && !user.getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        goods.setGoodsStatus(goodsStatusService.getGoodsById(2L));
        goodsService.deleteGoods(goods);
        return ResponseEntity.noContent().build();
    }

    // TODO створити власні ексепшени для всіх проблем які можуть бути в цьому конроллері
    // Приклад назв для двох кейсів нижче :
    // GoodsNotFoundException
    // GoodsAlreadyAddedException
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("addToFavorites/{goodsId}")
    public ResponseEntity<Goods> addToFavorites(Authentication auth, @PathVariable Long goodsId) {
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);

        if (goods == null) {
            throw new ForbiddenException("Goods not found");
        }

        if (user.getFavoriteGoods().contains(goods)) {
            throw new ForbiddenException("Goods have already been added to favorites");
        }
        user.getFavoriteGoods().add(goods);
        userService.save(UserDto.fromUser(user));
        return ResponseEntity.ok(goods);
    }

    // TODO створити власне виключення й кидати йогоу випадку винекниння проблем з загрузкою договора, а не RuntimeException
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/download/rental/{goodsId}")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long goodsId, @Valid @RequestBody RentalAgreement rental) {
        try {
            return rentalAgreementService.generateRentalAgreementResponse(rental, goodsId);
        } catch (IOException e) {
            throw new RuntimeException("Проблема з загрузкою договора оренди!");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/getcountnewgoods")
    public Integer findCountNewGoodsFromPeriod(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromDate,
                                               @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toDate) {
        return goodsService.getNewGoodsFromPeriod(fromDate, toDate);
    }

    @GetMapping("/random-goods")
    public List<Goods> getRandomGoods(
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(required = false, name = "quantity", defaultValue = "12") int quantity) {
        return goodsService.getRandomGoods(categoryName, quantity);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/filter")
    public Page<Goods> filterGoods(
            @RequestParam(name = "category", required = false) String categoryName,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            Pageable pageable) {
        return goodsService.filterGoods(categoryName, minPrice, maxPrice, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/{goodsId}/complaint")
    public ResponseEntity<ComplaintDTO> addComplaint
            (Authentication auth,
             @PathVariable Long goodsId,
             @Valid @RequestBody ComplaintDTO complaint) {
        Complaint newComplaint = complaintService.save(auth.getName(), goodsId, complaint);
        return ResponseEntity.ok(Converter.getDtoFromComplaint(newComplaint));
    }

    //TODO повертати не Long а створити модель й повертати її
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/total")
    public Long totalNumberOfGoods() {
        return goodsService.getTotalNumberOfGoods();
    }


    // TODO повертати не мапу а створити модель й повертати її
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/category/total")
    public ResponseEntity<Map<Category, Long>> amountOfGoodsByCategory() {
        Map<Category, Long> categoryAmounts = goodsService.getAmountOfGoodsByCategory();
        return ResponseEntity.ok(categoryAmounts);
    }

}