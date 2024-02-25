package com.airgear.controller;

import com.airgear.dto.GoodsDto;
import com.airgear.exception.ForbiddenException;
import com.airgear.model.goods.Goods;
import com.airgear.model.goods.GoodsStatus;
import com.airgear.model.User;
import com.airgear.repository.GoodsStatusRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsStatusRepository goodsStatusRepository;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/featured")
    public String user() {
        //TODO add return some random goods for home page
        return "Goods for home page";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public Goods createGoods(Authentication auth, @RequestBody GoodsDto goods) {
        User user = userService.findByUsername(auth.getName());
        Goods newGoods = goods.getGoodsFromDto();
        newGoods.setUser(user);
        GoodsStatus status = goodsStatusRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("GoodsStatus not found"));
        newGoods.setGoodsStatus(status);
        Goods savedGoods = goodsService.saveGoods(newGoods);
        return savedGoods;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{goodsId}")
    public ResponseEntity<Goods> getGoodsById(Authentication auth, @PathVariable Long goodsId) {
        log.info("auth name : {}", auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods == null) {
            throw new ForbiddenException("Goods not found");
        }
        if (!goods.getGoodsStatus().getName().equals("ACTIVE")) {
            throw new ForbiddenException("Goods was deleted");
        }

        return ResponseEntity.ok(goods);
    }

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

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(Authentication auth, @PathVariable Long goodsId) {
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        if (user.getId() != goods.getUser().getId() && !user.getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        goods.setGoodsStatus(new GoodsStatus(2, ""));
        goodsService.updateGoods(goods);
        return ResponseEntity.noContent().build();
    }

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
        userService.save(user);
        return ResponseEntity.ok(goods);
    }

}